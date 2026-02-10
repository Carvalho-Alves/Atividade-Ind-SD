package br.com.projeto.shared.messaging;

/**
 * Estados de uma mensagem dentro do Sender (mailbox).
 */
public enum MessageStatus {
  /** Mensagem pronta para ser entregue a algum Receiver. */
  NEW,
  /** Mensagem reservada/"em voo" para um Receiver (lease ativo). */
  LEASED,
  /** Mensagem confirmada (ack) pelo Receiver apos processamento. */
  ACKED,
  /** Mensagem excedeu tentativas e foi marcada como irrecuperavel. */
  DEAD
}
