package br.com.projeto.shared.processing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Resultado persistido do processamento (idempotencia / deduplicacao).
 *
 * <p>Papel: armazenar o resultado do processamento de uma mensagem identificada por {@code messageId}.
 * Em exercícios mais avançados (ex.: Q2/Q3), isso permite idempotência/deduplicação em caso de retry.
 */
@Entity
@Table(name = "processing_results")
public class ProcessingResultEntity {

  /**
   * Identificador da mensagem (messageId).
   *
   * <p>Estado/semântica: é a chave de idempotência. Se o Receiver reprocessar a mesma mensagem
   * (por retry), o ServerApp consegue detectar e devolver o mesmo resultado.
   */
  @Id
  @Column(name = "message_id", nullable = false)
  private UUID messageId;

  /**
   * Canal lógico no qual a mensagem foi processada (ex.: Q3_PUSH).
   *
   * <p>Estado: armazenado para auditoria e rastreabilidade do fluxo.
   */
  @Column(name = "channel", nullable = false)
  private String channel;

  /**
   * Instante de processamento/persistência do resultado.
   *
   * <p>Estado: indica quando o efeito foi gravado (útil para debugging e demonstração).
   */
  @Column(name = "processed_at", nullable = false)
  private Instant processedAt;

  /**
   * Resultado textual do processamento.
   *
   * <p>Estado: é retornado ao Receiver no processamento normal e no caminho de deduplicação.
   */
  @Column(name = "result", nullable = false, columnDefinition = "text")
  private String result;

  /**
   * Construtor protegido exigido pelo JPA.
   *
   * <p>Comportamento: usado pelo framework para materializar a entidade.
   */
  protected ProcessingResultEntity() {
  }

  /**
   * Constrói um resultado persistível.
   *
   * @param messageId id da mensagem (chave de idempotência).
   * @param channel canal lógico do fluxo.
   * @param processedAt instante de processamento.
   * @param result resultado produzido.
   */
  public ProcessingResultEntity(UUID messageId, String channel, Instant processedAt, String result) {
    this.messageId = messageId;
    this.channel = channel;
    this.processedAt = processedAt;
    this.result = result;
  }

  /**
   * Retorna o id da mensagem associada.
   *
   * @return messageId.
   */
  public UUID getMessageId() {
    return messageId;
  }

  /**
   * Retorna o canal lógico.
   *
   * @return canal.
   */
  public String getChannel() {
    return channel;
  }

  /**
   * Retorna o instante do processamento.
   *
   * @return processedAt.
   */
  public Instant getProcessedAt() {
    return processedAt;
  }

  /**
   * Retorna o resultado persistido.
   *
   * @return texto do resultado.
   */
  public String getResult() {
    return result;
  }
}
