package br.com.projeto.q2.receiver;

import br.com.projeto.q2.proto.sender.AckMessageRequest;
import br.com.projeto.q2.proto.sender.NackMessageRequest;
import br.com.projeto.q2.proto.sender.PullMessagesRequest;
import br.com.projeto.q2.proto.sender.PullMessagesResponse;
import br.com.projeto.q2.proto.sender.SenderServiceGrpc;
import br.com.projeto.q2.proto.server.ProcessRequest;
import br.com.projeto.q2.proto.server.ServerServiceGrpc;
import br.com.projeto.shared.messaging.MessageChannel;
import br.com.projeto.shared.processing.ProcessingResultEntity;
import br.com.projeto.shared.processing.ProcessingResultRepository;
import io.grpc.StatusRuntimeException;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Poller do nó Receiver (Questão 02).
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Fazer pulling de mensagens no Sender via gRPC;</li>
 *   <li>Encaminhar a mensagem para o Server via gRPC (processamento);</li>
 *   <li>Persistir o resultado e emitir ACK; em falhas, emitir NACK para retry.</li>
 * </ul>
 */
@Component
public class Q2ReceiverPoller {

  private static final Logger log = LoggerFactory.getLogger(Q2ReceiverPoller.class);

  private final SenderServiceGrpc.SenderServiceBlockingStub sender;
  private final ServerServiceGrpc.ServerServiceBlockingStub server;
  private final ProcessingResultRepository results;
  private final Clock clock;
  private final String receiverId;

  /**
   * Constrói o poller do Receiver.
   *
   * @param sender stub gRPC para o Sender.
   * @param server stub gRPC para o Server.
   * @param results repositório de resultados (persistência do processamento).
   * @param clock relógio injetável.
   */
  public Q2ReceiverPoller(
      @GrpcClient("sender") SenderServiceGrpc.SenderServiceBlockingStub sender,
      @GrpcClient("server") ServerServiceGrpc.ServerServiceBlockingStub server,
      ProcessingResultRepository results,
      Clock clock
  ) {
    this.sender = sender;
    this.server = server;
    this.results = results;
    this.clock = clock;
    this.receiverId = System.getenv().getOrDefault("HOSTNAME", "receiver");
  }

  /**
   * Execução cíclica do polling.
   *
   * <p>Comportamento: busca mensagens do Sender, processa no Server e finaliza com ACK.
   * Em falhas transitórias, emite NACK para liberar retry.
   */
  @Scheduled(fixedDelayString = "${app.receiver.poll-interval:2s}")
  public void pollOnce() {
    PullMessagesResponse pulled;
    try {
      pulled = sender.pullMessages(PullMessagesRequest.newBuilder()
          .setMaxMessages(1)
          .setReceiverId(receiverId)
          .build());
    } catch (StatusRuntimeException ex) {
      log.warn("Q2 falha ao puxar mensagens (Sender indisponível no momento?): {}", ex.toString());
      return;
    }

    if (pulled.getMessagesCount() == 0) {
      return;
    }

    for (var msg : pulled.getMessagesList()) {
      String leaseToken = msg.getLeaseToken();
      UUID messageId;
      try {
        messageId = UUID.fromString(msg.getMessageId());
      } catch (Exception ex) {
        log.warn("Q2 idMensagem inválido recebido: {}", msg.getMessageId());
        continue;
      }

      try {
        if (results.existsById(messageId)) {
          var ack = sender.ackMessage(AckMessageRequest.newBuilder()
              .setMessageId(messageId.toString())
              .setLeaseToken(leaseToken)
              .build());
          log.info("Q2 mensagem já processada idMensagem={}, ack={}", messageId, ack.getAcked());
          continue;
        }

        var processed = server.process(ProcessRequest.newBuilder()
            .setMessageId(messageId.toString())
            .setPayload(msg.getPayload())
            .build());

        results.save(new ProcessingResultEntity(
            messageId,
            MessageChannel.Q2_GRPC,
            Instant.now(clock),
            processed.getResult()
        ));

        var ack = sender.ackMessage(AckMessageRequest.newBuilder()
            .setMessageId(messageId.toString())
            .setLeaseToken(leaseToken)
            .build());

        log.info("Q2 mensagem entregue idMensagem={}, resultado={}, ack={}", messageId, processed.getResult(), ack.getAcked());
      } catch (StatusRuntimeException ex) {
        try {
          sender.nackMessage(NackMessageRequest.newBuilder()
              .setMessageId(messageId.toString())
              .setLeaseToken(leaseToken)
              .setError(ex.toString())
              .build());
        } catch (Exception ignore) {
          // Ignora: o Sender pode estar temporariamente indisponível.
        }
        log.warn("Q2 falha ao processar idMensagem={}, vai tentar novamente. erro={}", messageId, ex.toString());
      } catch (Exception ex) {
        try {
          sender.nackMessage(NackMessageRequest.newBuilder()
              .setMessageId(messageId.toString())
              .setLeaseToken(leaseToken)
              .setError(ex.toString())
              .build());
        } catch (Exception ignore) {
          // Ignora: o Sender pode estar temporariamente indisponível.
        }
        log.warn("Q2 falha ao processar idMensagem={}, vai tentar novamente. erro={}", messageId, ex.toString());
      }
    }
  }
}
