package br.com.projeto.shared.messaging;

/**
 * "Canal" logico para separar fluxos.
 */
public enum MessageChannel {
  /** Canal usado na Questão 02 (gRPC): fluxo Sender gRPC -> Receiver gRPC -> Server gRPC. */
  Q2_GRPC
}
