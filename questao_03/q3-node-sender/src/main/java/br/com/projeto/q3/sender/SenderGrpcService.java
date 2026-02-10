package br.com.projeto.q3.sender;

import br.com.projeto.q3.proto.sender.AckMessageRequest;
import br.com.projeto.q3.proto.sender.AckMessageResponse;
import br.com.projeto.q3.proto.sender.NackMessageRequest;
import br.com.projeto.q3.proto.sender.NackMessageResponse;
import br.com.projeto.q3.proto.sender.PushedMessage;
import br.com.projeto.q3.proto.sender.SenderServiceGrpc;
import br.com.projeto.q3.proto.sender.SendMessageRequest;
import br.com.projeto.q3.proto.sender.SendMessageResponse;
import br.com.projeto.q3.proto.sender.SubscribeRequest;
import br.com.projeto.shared.messaging.LeasedMessage;
import br.com.projeto.shared.messaging.MessageChannel;
import br.com.projeto.shared.messaging.MessageStore;
import io.grpc.Status;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Optional;
import java.util.UUID;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * Implementação do serviço gRPC do nó Sender (Questão 03).
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Receber mensagens do Client via gRPC e enfileirar no mailbox (Postgres);</li>
 *   <li>Fazer push para o Receiver via {@code Subscribe} (server-streaming);</li>
 *   <li>Aplicar ACK/NACK para confirmar ou liberar mensagens para retry.</li>
 * </ul>
 */
@GrpcService
public class SenderGrpcService extends SenderServiceGrpc.SenderServiceImplBase {

  /** Abstração do mailbox persistente (Postgres). */
  private final MessageStore store;

  /**
   * Sinalização simples para acordar o loop de push quando uma nova mensagem chega.
   *
   * <p>Observação: este mecanismo é propositalmente simples para o laboratório.
   */
  private final Object newMessageSignal = new Object();

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
    UUID id = store.enqueue(MessageChannel.Q3_GRPC, payload, null);
    responseObserver.onNext(SendMessageResponse.newBuilder().setMessageId(id.toString()).build());
    responseObserver.onCompleted();

    synchronized (newMessageSignal) {
      newMessageSignal.notifyAll();
    }
  }

  /**
   * Assina o fluxo de mensagens (Receiver -> Sender) usando server-streaming.
   *
   * <p>Comportamento: enquanto a conexão estiver ativa, tenta reservar uma mensagem e empurrar ao
   * Receiver. Se não houver mensagens, aguarda sinalização ou timeout curto.
   *
   * @param request request contendo {@code receiverId}.
   * @param responseObserver stream de mensagens empurradas.
   */
  @Override
  public void subscribe(SubscribeRequest request, StreamObserver<PushedMessage> responseObserver) {
    String receiverId = request.getReceiverId();
    if (receiverId == null || receiverId.isBlank()) {
      responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("receiverId é obrigatório").asRuntimeException());
      return;
    }

    AtomicBoolean cancelled = new AtomicBoolean(false);
    if (responseObserver instanceof ServerCallStreamObserver<PushedMessage> serverObserver) {
      serverObserver.setOnCancelHandler(() -> {
        cancelled.set(true);
        synchronized (newMessageSignal) {
          newMessageSignal.notifyAll();
        }
      });
    }

    try {
      while (!cancelled.get()) {
        Optional<LeasedMessage> leased = store.tryDeliver(MessageChannel.Q3_GRPC, receiverId);
        if (leased.isPresent()) {
          LeasedMessage msg = leased.get();
          responseObserver.onNext(PushedMessage.newBuilder()
              .setMessageId(msg.id().toString())
              .setPayload(msg.payload())
              .setLeaseToken(buildLeaseToken(receiverId, msg.id()))
              .setLeasedUntilEpochMillis(msg.lockedUntil().toEpochMilli())
              .setAttempt(msg.attemptCount())
              .build());
          continue;
        }

        synchronized (newMessageSignal) {
          newMessageSignal.wait(5_000);
        }
      }

      responseObserver.onCompleted();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
      responseObserver.onError(Status.CANCELLED.withCause(ex).asRuntimeException());
    } catch (RuntimeException ex) {
      responseObserver.onError(ex);
    }
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

    synchronized (newMessageSignal) {
      newMessageSignal.notifyAll();
    }
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

    synchronized (newMessageSignal) {
      newMessageSignal.notifyAll();
    }
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
