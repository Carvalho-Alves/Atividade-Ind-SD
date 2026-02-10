package br.com.projeto.shared.messaging;

/**
 * Estados do ciclo de vida de uma mensagem no mailbox.
 */
public enum MessageStatus {
  /** Mensagem recém-criada e ainda não entregue. */
  NEW,

  /** Mensagem alugada (em posse temporária) por um receptor. */
  LEASED,

  /** Mensagem confirmada (ACK) e finalizada com sucesso. */
  ACKED,

  /** Mensagem excedeu tentativas e foi marcada como DEAD. */
  DEAD
}
