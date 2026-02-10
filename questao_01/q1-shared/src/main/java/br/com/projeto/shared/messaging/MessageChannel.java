package br.com.projeto.shared.messaging;

/**
 * "Canal" logico para separar fluxos entre questoes.
 *
 * Observacao: na vida real isso poderia ser um topico/fila (ex.: Kafka/Rabbit),
 * mas aqui usamos Postgres como "mailbox" para demonstrar comunicacao indireta.
 */
public enum MessageChannel {
  /** Canal usado na Questão 1 (REST): mailbox para fluxo ClientApp -> Sender -> Receiver -> ServerApp. */
  Q1_REST,

  /** Canal usado na Questão 2 (gRPC): mailbox para fluxo Sender gRPC -> Receiver gRPC -> ServerApp gRPC. */
  Q2_GRPC,

  /** Canal usado na Questão 3 (gRPC + garantia): mailbox do fluxo com idempotência + hints de push. */
  Q3_PUSH
}
