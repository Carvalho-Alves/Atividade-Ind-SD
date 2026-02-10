package br.com.projeto.q2.sender;

import br.com.projeto.q2.proto.sender.AckMessageRequest;
import br.com.projeto.q2.proto.sender.AckMessageResponse;
import br.com.projeto.q2.proto.sender.NackMessageRequest;
import br.com.projeto.q2.proto.sender.NackMessageResponse;
import br.com.projeto.q2.proto.sender.PullMessagesRequest;
import br.com.projeto.q2.proto.sender.PullMessagesResponse;
import br.com.projeto.q2.proto.sender.SenderServiceGrpc;
import br.com.projeto.q2.proto.sender.SendMessageRequest;
import br.com.projeto.q2.proto.sender.SendMessageResponse;
import br.com.projeto.shared.messaging.LeasedMessage;
import br.com.projeto.shared.messaging.MessageChannel;
import br.com.projeto.shared.messaging.MessageStore;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * Implementação do serviço gRPC do nó Sender (Questão 02).
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Receber mensagens do Client via gRPC e enfileirar no mailbox (Postgres);</li>
 *   <li>Permitir que o Receiver faça pulling (try-delivery) via {@code PullMessages};</li>
 *   <li>Aplicar ACK/NACK para confirmar ou liberar mensagens para retry.</li>
 * </ul>
 */
@GrpcService
public class SenderGrpcService extends SenderServiceGrpc.SenderServiceImplBase {

  /**
   * Abstração do mailbox persistente.
   *
   * <p>Papel: persistir mensagens e controlar leasing/ACK/NACK.
   */
  private final MessageStore store;

  /**
   * Constrói o serviço do Sender.
   *
   * @param store implementação do mailbox.
   */
  public SenderGrpcService(MessageStore store) {
    this.store = store;
  }

  /**
   * Publica uma mensagem no mailbox (Client -> Sender).
   *
   * @param request request com payload.
   * @param responseObserver observer para enviar resposta ao cliente.
   */
  @Override
  public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
    String payload = request.getPayload();
    UUID id = store.enqueue(MessageChannel.Q2_GRPC, payload, null);
    responseObserver.onNext(SendMessageResponse.newBuilder().setMessageId(id.toString()).build());
    responseObserver.onCompleted();
  }

  /**
   * Faz pulling de mensagens para o Receiver (Receiver -> Sender).
   *
   * <p>Comportamento: tenta entregar até {@code maxMessages}, respeitando limites de segurança.
   *
   * @param request request contendo {@code receiverId} e limite.
   * @param responseObserver observer para retornar a lista de mensagens leased.
   */
  @Override
  public void pullMessages(PullMessagesRequest request, StreamObserver<PullMessagesResponse> responseObserver) {
    String receiverId = request.getReceiverId();
    if (receiverId == null || receiverId.isBlank()) {
      responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("receiverId é obrigatório").asRuntimeException());
      return;
    }

    int max = request.getMaxMessages();
    if (max <= 0) {
      max = 1;
    }
    if (max > 100) {
      max = 100;
    }

    PullMessagesResponse.Builder response = PullMessagesResponse.newBuilder();
    for (int i = 0; i < max; i++) {
      Optional<LeasedMessage> leased = store.tryDeliver(MessageChannel.Q2_GRPC, receiverId);
      if (leased.isEmpty()) {
        break;
      }
      LeasedMessage msg = leased.get();
      response.addMessages(br.com.projeto.q2.proto.sender.LeasedMessage.newBuilder()
          .setMessageId(msg.id().toString())
          .setPayload(msg.payload())
          .setLeaseToken(buildLeaseToken(receiverId, msg.id()))
          .setLeasedUntilEpochMillis(msg.lockedUntil().toEpochMilli())
          .setAttempt(msg.attemptCount())
          .build());
    }

    responseObserver.onNext(response.build());
    responseObserver.onCompleted();
  }

  @Override
  public void ackMessage(AckMessageRequest request, StreamObserver<AckMessageResponse> responseObserver) {
    UUID messageId = parseUuidOrFail(request.getMessageId());
    String receiverId = parseReceiverIdFromToken(request.getLeaseToken());
    if (receiverId == null) {
      responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("leaseToken inválido").asRuntimeException());
      return;
    }

    boolean acked = store.ack(messageId, receiverId);
    responseObserver.onNext(AckMessageResponse.newBuilder().setAcked(acked).build());
    responseObserver.onCompleted();
  }

  @Override
  public void nackMessage(NackMessageRequest request, StreamObserver<NackMessageResponse> responseObserver) {
    UUID messageId = parseUuidOrFail(request.getMessageId());
    String receiverId = parseReceiverIdFromToken(request.getLeaseToken());
    if (receiverId == null) {
      responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("leaseToken inválido").asRuntimeException());
      return;
    }

    String error = request.getError();
    if (error == null || error.isBlank()) {
      error = "NACK em " + Instant.now();
    }

    store.nack(messageId, receiverId, error);
    responseObserver.onNext(NackMessageResponse.newBuilder().setNacked(true).build());
    responseObserver.onCompleted();
  }

  private static String buildLeaseToken(String receiverId, UUID messageId) {
    return receiverId + ":" + messageId;
  }

  /**
   * Faz parse de UUID e falha com erro gRPC caso seja inválido.
   *
   * @param value string com UUID.
   * @return UUID parseado.
   */
  private static UUID parseUuidOrFail(String value) {
    try {
      return UUID.fromString(value);
    } catch (Exception ex) {
      throw Status.INVALID_ARGUMENT.withDescription("UUID inválido").withCause(ex).asRuntimeException();
    }
  }

  /**
   * Extrai o {@code receiverId} do {@code leaseToken}.
   *
   * <p>Formato esperado: {@code receiverId:messageId}.
   *
   * @param token token informado pelo Receiver.
   * @return receiverId ou {@code null} se inválido.
   */
  private static String parseReceiverIdFromToken(String token) {
    if (token == null) {
      return null;
    }
    int idx = token.indexOf(':');
    if (idx <= 0) {
      return null;
    }
    String receiverId = token.substring(0, idx);
    return receiverId.isBlank() ? null : receiverId;
  }
}
