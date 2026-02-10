package br.com.projeto.shared.messaging;

import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de persistência da mailbox de mensagens.
 *
 * <p>Operações principais:
 * <ul>
 *   <li>{@link #enqueue(MessageChannel, String, UUID)}: publica uma mensagem</li>
 *   <li>{@link #tryDeliver(MessageChannel, String)}: tenta "alugar" (lease) uma mensagem para um receptor</li>
 *   <li>{@link #ack(UUID, String)}: confirma processamento bem-sucedido</li>
 *   <li>{@link #nack(UUID, String, String)}: registra falha e libera para nova tentativa</li>
 * </ul>
 */
public interface MessageStore {

  /**
   * Insere (ou atualiza) uma mensagem no mailbox.
   *
   * @param channel canal lógico da mensagem
   * @param payload corpo/conteúdo a ser processado
   * @param messageId id opcional; se {@code null} um novo UUID é gerado
   * @return id efetivo da mensagem persistida
   */
  UUID enqueue(MessageChannel channel, String payload, UUID messageId);

  /**
   * Tenta obter uma mensagem para entrega, realizando lease/lock no banco.
   *
   * @param channel canal da mensagem
   * @param receiverId identificador do receptor que está solicitando a entrega
   * @return uma mensagem alugada, se houver candidata
   */
  Optional<LeasedMessage> tryDeliver(MessageChannel channel, String receiverId);

  /**
   * Confirma (ACK) que a mensagem foi processada com sucesso.
   *
   * @param messageId id da mensagem
   * @param receiverId receptor que está confirmando
   * @return {@code true} se o ACK foi aplicado; {@code false} caso contrário
   */
  boolean ack(UUID messageId, String receiverId);

  /**
   * Registra uma falha (NACK) e libera a mensagem para retentativa.
   *
   * @param messageId id da mensagem
   * @param receiverId receptor que está registrando a falha
   * @param error mensagem de erro/diagnóstico
   */
  void nack(UUID messageId, String receiverId, String error);
}
