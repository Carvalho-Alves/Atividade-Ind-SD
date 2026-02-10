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
 * Implementacao do mailbox do Sender usando Postgres.
 *
 * Por que Postgres aqui?
 * - Simplifica o laboratorio: sem precisar de Rabbit/Kafka.
 * - Permite demonstrar "indirect communication" (mailbox) + leasing + retries.
 * - Com SELECT ... FOR UPDATE SKIP LOCKED conseguimos concorrencia segura.
 */
@Repository
public class PostgresMessageStore implements MessageStore {

  /**
   * Gateway JDBC para executar SQL parametrizado.
   *
   * <p>Papel: persistir e recuperar mensagens do mailbox via Postgres.
   */
  private final NamedParameterJdbcTemplate jdbc;

  /**
   * Relógio injetável.
   *
   * <p>Papel: controlar "agora" de maneira consistente (útil em testes e para reduzir acoplamento
   * ao {@code Instant.now()} espalhado).
   */
  private final Clock clock;

  /**
   * Propriedades de aplicação (lease durations, max attempts, etc.).
   *
   * <p>Estado: define parâmetros operacionais do mailbox (tempo de lock e limites de retry).
   */
  private final br.com.projeto.shared.config.AppProperties properties;

  /**
   * Constrói a implementação de mailbox baseada em Postgres.
   *
   * @param jdbc template JDBC para executar operações.
   * @param clock clock injetável.
   * @param properties configurações do lease/retry.
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

  /**
   * Enfileira (publica) uma mensagem no mailbox persistente.
   *
   * <p>Comportamento:
   * <ul>
   *   <li>Gera {@code UUID} se {@code messageId} vier nulo;</li>
   *   <li>Persiste a mensagem como {@code NEW};</li>
   *   <li>Faz um upsert simples por {@code id} para tolerar reenvio do cliente (idempotência de publicação).</li>
   * </ul>
   *
   * @param channel canal lógico da mensagem.
   * @param payload conteúdo da mensagem.
   * @param messageId id opcional definido pelo cliente.
   * @return id efetivo da mensagem persistida.
   */
  @Override
  @Transactional
  public UUID enqueue(MessageChannel channel, String payload, UUID messageId) {
    UUID id = (messageId != null) ? messageId : UUID.randomUUID();
    Instant now = Instant.now(clock);
    Timestamp nowTs = Timestamp.from(now);

    // Upsert simples para suportar idempotencia (caso o cliente caia e reenvie).
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

  /**
   * Tenta entregar exatamente uma mensagem elegível via mecanismo de pulling.
   *
   * <p>Comportamento:
   * <ul>
   *   <li>Seleciona uma mensagem {@code NEW} (ou {@code LEASED} com lease expirado);</li>
   *   <li>Usa {@code FOR UPDATE SKIP LOCKED} para concorrência segura entre múltiplos Receivers;</li>
   *   <li>Atualiza a mensagem para {@code LEASED}, define {@code lock_owner}/{@code locked_until} e incrementa tentativas.</li>
   * </ul>
   *
   * @param channel canal lógico de onde puxar.
   * @param receiverId identificador do Receiver solicitante.
   * @return {@link Optional} com a mensagem leased ou vazio se nada estiver elegível.
   */
  @Override
  @Transactional
  public Optional<LeasedMessage> tryDeliver(MessageChannel channel, String receiverId) {
    Instant now = Instant.now(clock);
    Instant lockUntil = now.plus(properties.getLease().getLockDuration());
    Timestamp nowTs = Timestamp.from(now);
    Timestamp lockUntilTs = Timestamp.from(lockUntil);

    // Estrategia:
    // - Seleciona 1 mensagem elegivel (NEW ou lease expirado) e faz lock com SKIP LOCKED
    // - Atualiza status para LEASED e incrementa attempt_count
    // - Retorna a linha atualizada
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

  /**
   * Confirma (ACK) uma mensagem leased.
   *
   * <p>Comportamento: somente confirma se:
   * <ul>
   *   <li>o status atual é {@code LEASED};</li>
   *   <li>o {@code receiverId} é o {@code lock_owner};</li>
   *   <li>o lease ainda não expirou.</li>
   * </ul>
   *
   * @param messageId id da mensagem.
   * @param receiverId receiver que detém o lock.
   * @return true se a atualização ocorreu.
   */
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

  /**
   * NACK de uma mensagem leased.
   *
   * <p>Comportamento:
   * <ul>
   *   <li>Libera a mensagem imediatamente (volta para {@code NEW}) para permitir retry;</li>
   *   <li>Registra o erro em {@code last_error};</li>
   *   <li>Se {@code attempt_count} atingiu o máximo, marca como {@code DEAD} (DLQ simplificada).</li>
   * </ul>
   *
   * @param messageId id da mensagem.
   * @param receiverId receiver que estava processando.
   * @param error mensagem de erro/diagnóstico.
   */
  @Override
  @Transactional
  public void nack(UUID messageId, String receiverId, String error) {
    Instant now = Instant.now(clock);
    Timestamp nowTs = Timestamp.from(now);

    // NACK libera a mensagem para nova tentativa imediatamente.
    // Isso modela o comportamento de reprocessamento rapido em laboratorio.
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

    // Se excedeu tentativas, marca DEAD ("DLQ" simplificada).
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
