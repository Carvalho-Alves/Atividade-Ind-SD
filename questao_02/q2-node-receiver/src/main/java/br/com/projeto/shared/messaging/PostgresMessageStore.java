package br.com.projeto.shared.messaging;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação {@link MessageStore} baseada em PostgreSQL.
 *
 * <p>Responsável por aplicar lease/lock, contabilizar tentativas, registrar ACK/NACK e mover
 * mensagens para DEAD ao exceder o limite.
 */
@Repository
public class PostgresMessageStore implements MessageStore {

  /** Template JDBC com suporte a parâmetros nomeados. */
  private final NamedParameterJdbcTemplate jdbc;

  /** Relógio injetável para timestamps consistentes e testabilidade. */
  private final Clock clock;

  /** Propriedades da aplicação (duração de lease, máximo de tentativas). */
  private final br.com.projeto.shared.config.AppProperties properties;

  /**
   * Cria um store PostgreSQL.
   *
   * @param jdbc template JDBC
   * @param clock relógio
   * @param properties propriedades da aplicação
   */
  public PostgresMessageStore(
      NamedParameterJdbcTemplate jdbc,
      Clock clock,
      br.com.projeto.shared.config.AppProperties properties
  ) {
    this.jdbc = jdbc;
    this.clock = clock;
    this.properties = properties;
  }

  @Override
  @Transactional
  public UUID enqueue(MessageChannel channel, String payload, UUID messageId) {
    UUID id = (messageId != null) ? messageId : UUID.randomUUID();
    Instant now = Instant.now(clock);
    Timestamp nowTs = Timestamp.from(now);

    String sql = """
        INSERT INTO messages (id, channel, payload, status, attempt_count, created_at, updated_at)
        VALUES (:id, :channel, :payload, :status, 0, :now, :now)
        ON CONFLICT (id) DO UPDATE
          SET payload = EXCLUDED.payload,
              updated_at = EXCLUDED.updated_at
        """;

    jdbc.update(sql, new MapSqlParameterSource()
        .addValue("id", id)
        .addValue("channel", channel.name())
        .addValue("payload", payload)
        .addValue("status", MessageStatus.NEW.name())
        .addValue("now", nowTs, Types.TIMESTAMP));

    return id;
  }

  @Override
  @Transactional
  public Optional<LeasedMessage> tryDeliver(MessageChannel channel, String receiverId) {
    Instant now = Instant.now(clock);
    Instant lockUntil = now.plus(properties.getLease().getLockDuration());
    Timestamp nowTs = Timestamp.from(now);
    Timestamp lockUntilTs = Timestamp.from(lockUntil);

    String sql = """
        WITH candidate AS (
          SELECT id
          FROM messages
          WHERE channel = :channel
            AND status IN ('NEW', 'LEASED')
            AND (
              status = 'NEW'
              OR locked_until IS NULL
              OR locked_until < :now
            )
            AND attempt_count < :maxAttempts
          ORDER BY created_at
          FOR UPDATE SKIP LOCKED
          LIMIT 1
        )
        UPDATE messages m
        SET status = 'LEASED',
            lock_owner = :receiverId,
            locked_until = :lockUntil,
            attempt_count = attempt_count + 1,
            updated_at = :now,
            last_error = NULL
        FROM candidate
        WHERE m.id = candidate.id
        RETURNING m.id, m.channel, m.payload, m.attempt_count, m.lock_owner, m.locked_until
        """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("channel", channel.name())
        .addValue("receiverId", receiverId)
        .addValue("now", nowTs, Types.TIMESTAMP)
        .addValue("lockUntil", lockUntilTs, Types.TIMESTAMP)
        .addValue("maxAttempts", properties.getLease().getMaxAttempts());

    return jdbc.query(sql, params, rs -> {
      if (!rs.next()) {
        return Optional.empty();
      }
      return Optional.of(new LeasedMessage(
          UUID.fromString(rs.getString("id")),
          MessageChannel.valueOf(rs.getString("channel")),
          rs.getString("payload"),
          rs.getInt("attempt_count"),
          rs.getString("lock_owner"),
          rs.getTimestamp("locked_until").toInstant()
      ));
    });
  }

  @Override
  @Transactional
  public boolean ack(UUID messageId, String receiverId) {
    Instant now = Instant.now(clock);
    Timestamp nowTs = Timestamp.from(now);

    String sql = """
        UPDATE messages
        SET status = 'ACKED',
            updated_at = :now
        WHERE id = :id
          AND status = 'LEASED'
          AND lock_owner = :receiverId
          AND locked_until IS NOT NULL
          AND locked_until >= :now
        """;

    int updated = jdbc.update(sql, new MapSqlParameterSource()
        .addValue("id", messageId)
        .addValue("receiverId", receiverId)
        .addValue("now", nowTs, Types.TIMESTAMP));

    return updated == 1;
  }

  @Override
  @Transactional
  public void nack(UUID messageId, String receiverId, String error) {
    Instant now = Instant.now(clock);
    Timestamp nowTs = Timestamp.from(now);

    String sql = """
        UPDATE messages
        SET status = 'NEW',
            lock_owner = NULL,
            locked_until = NULL,
            last_error = :error,
            updated_at = :now
        WHERE id = :id
          AND status = 'LEASED'
          AND lock_owner = :receiverId
        """;

    jdbc.update(sql, new MapSqlParameterSource()
        .addValue("id", messageId)
        .addValue("receiverId", receiverId)
        .addValue("error", error)
        .addValue("now", nowTs, Types.TIMESTAMP));

    String deadSql = """
        UPDATE messages
        SET status = 'DEAD',
            updated_at = :now
        WHERE id = :id
          AND attempt_count >= :maxAttempts
        """;
    jdbc.update(deadSql, new MapSqlParameterSource()
        .addValue("id", messageId)
        .addValue("maxAttempts", properties.getLease().getMaxAttempts())
        .addValue("now", nowTs, Types.TIMESTAMP));
  }
}
