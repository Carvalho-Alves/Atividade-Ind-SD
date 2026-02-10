package br.com.projeto.shared.messaging;

/**
 * "Canal" logico para separar fluxos.
 */
public enum MessageChannel {
  /** Canal usado na Questão 03 (gRPC Push): Sender (stream) -> Receiver -> Server gRPC. */
  Q3_GRPC
}
