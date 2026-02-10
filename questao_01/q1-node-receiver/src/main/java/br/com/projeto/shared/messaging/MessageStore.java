package br.com.projeto.shared.messaging;

import java.util.Optional;
import java.util.UUID;

/**
 * Abstracao do "mailbox" do Sender.
 *
 * Conceitos chave:
 * - enqueue: ClientApp publica ("dispare-e-esqueça")
 * - tryDeliver: Receiver faz pulling e tenta obter 1 mensagem
 * - ack: Receiver confirma sucesso (garantia de entrega em pelo menos uma vez)
 */
public interface MessageStore {

  /**
   * Enfileira uma mensagem no mailbox.
   *
    * @param channel canal lógico do fluxo (Q1/Q2/Q3).
    * @param payload conteúdo da mensagem.
    * @param messageId opcional: se presente, permite idempotência de publicação por id.
    * @return id efetivo persistido no mailbox.
   */
  UUID enqueue(MessageChannel channel, String payload, UUID messageId);

  /**
   * Tenta entregar (via pulling) uma unica mensagem.
   *
   * Semantica: "try delivery".
   * - Se existir mensagem elegivel, faz lease e retorna.
   * - Se nao existir, retorna vazio.
    *
    * @param channel canal lógico de onde puxar.
    * @param receiverId identificador do Receiver solicitante (dono do lease).
    * @return mensagem alugada (leased) ou vazio se nada estiver elegível.
   */
  Optional<LeasedMessage> tryDeliver(MessageChannel channel, String receiverId);

  /**
   * Confirma uma mensagem (ACK).
   *
   * <p>Comportamento: marca a mensagem como confirmada após processamento bem-sucedido.
   * Para ser válida, a mensagem precisa estar no estado LEASED e o {@code receiverId}
   * precisa ser o dono do lease.
   *
   * @param messageId id da mensagem a ser confirmada.
   * @param receiverId identificador do Receiver (dono do lease).
   * @return {@code true} se o ACK foi aplicado; {@code false} caso contrário (ex.: lease expirou).
   */
  boolean ack(UUID messageId, String receiverId);

  /**
   * Sinaliza falha no processamento (NACK).
   *
   * <p>Comportamento: libera a mensagem para nova tentativa (retry) e registra um erro.
   * A política de quantas tentativas são permitidas é definida por configuração.
   *
   * @param messageId id da mensagem que falhou.
   * @param receiverId identificador do Receiver que estava processando.
   * @param error descrição do erro (para diagnóstico).
   */
  void nack(UUID messageId, String receiverId, String error);
}
