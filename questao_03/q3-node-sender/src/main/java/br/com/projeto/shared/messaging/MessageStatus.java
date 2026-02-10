package br.com.projeto.shared.messaging;

/**
 * Estados de uma mensagem dentro do mailbox.
 */
public enum MessageStatus {
  NEW,
  LEASED,
  ACKED,
  DEAD
}
