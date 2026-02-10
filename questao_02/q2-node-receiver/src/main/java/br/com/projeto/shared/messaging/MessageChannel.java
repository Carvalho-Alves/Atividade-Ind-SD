package br.com.projeto.shared.messaging;

/**
 * Canal lógico de mensagens.
 *
 * <p>Usado para separar tipos de tráfego dentro da mesma tabela de mailbox.
 */
public enum MessageChannel {
  /** Canal referente à Questão 02 via gRPC. */
  Q2_GRPC
}
