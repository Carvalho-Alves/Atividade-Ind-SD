package br.com.projeto.shared.processing;

import br.com.projeto.shared.messaging.MessageChannel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidade JPA que registra o resultado do processamento de uma mensagem.
 *
 * <p>Usada para idempotência: se um {@code messageId} já foi processado com sucesso, o receptor
 * pode evitar reprocessamento em entregas duplicadas.
 */
@Entity
@Table(name = "processing_results")
public class ProcessingResultEntity {

  /** Identificador da mensagem processada. */
  @Id
  @Column(name = "message_id", nullable = false)
  private UUID messageId;

  /** Canal lógico da mensagem. */
  @Enumerated(EnumType.STRING)
  @Column(name = "channel", nullable = false, length = 32)
  private MessageChannel channel;

  /** Instante em que o processamento foi concluído com sucesso. */
  @Column(name = "processed_at", nullable = false)
  private Instant processedAt;

  /** Resultado do processamento (texto). */
  @Column(name = "result", nullable = false, columnDefinition = "text")
  private String result;

  /** Construtor padrão exigido pelo JPA. */
  protected ProcessingResultEntity() {
  }

  /**
   * Constrói um registro de resultado de processamento.
   *
   * @param messageId id da mensagem
   * @param channel canal lógico
   * @param processedAt instante de processamento
   * @param result resultado do processamento
   */
  public ProcessingResultEntity(UUID messageId, MessageChannel channel, Instant processedAt, String result) {
    this.messageId = messageId;
    this.channel = channel;
    this.processedAt = processedAt;
    this.result = result;
  }

  /** @return id da mensagem processada. */
  public UUID getMessageId() {
    return messageId;
  }

  /** @return canal da mensagem. */
  public MessageChannel getChannel() {
    return channel;
  }

  /** @return instante do processamento. */
  public Instant getProcessedAt() {
    return processedAt;
  }

  /** @return resultado do processamento. */
  public String getResult() {
    return result;
  }
}
