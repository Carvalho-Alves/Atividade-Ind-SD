package br.com.projeto.q3.receiver;

import br.com.projeto.q3.proto.sender.AckMessageRequest;
import br.com.projeto.q3.proto.sender.NackMessageRequest;
import br.com.projeto.q3.proto.sender.PushedMessage;
import br.com.projeto.q3.proto.sender.SenderServiceGrpc;
import br.com.projeto.q3.proto.sender.SubscribeRequest;
import br.com.projeto.q3.proto.server.ProcessRequest;
import br.com.projeto.q3.proto.server.ServerServiceGrpc;
import br.com.projeto.shared.messaging.MessageChannel;
import br.com.projeto.shared.processing.ProcessingResultEntity;
import br.com.projeto.shared.processing.ProcessingResultRepository;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Subscriber do nó Receiver (Questão 03).
 *
 * <p>Papel: assinar o stream {@code Subscribe} do Sender (push) e processar mensagens no Server.
 * A confirmação (ACK) só ocorre após persistir o resultado; em falhas, emite NACK para retry.
 */
@Component
public class Q3ReceiverSubscriber implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(Q3ReceiverSubscriber.class);

  /** Stub assíncrono do Sender (server-streaming). */
  private final SenderServiceGrpc.SenderServiceStub senderAsync;

  /** Stub bloqueante do Sender (ACK/NACK). */
  private final SenderServiceGrpc.SenderServiceBlockingStub senderBlocking;

  /** Stub bloqueante do Server (processamento). */
  private final ServerServiceGrpc.ServerServiceBlockingStub server;

  /** Repositório de resultados (idempotência/deduplicação). */
  private final ProcessingResultRepository results;

  /** Relógio injetável para obter {@link Instant} consistente. */
  private final Clock clock;

  /** Executor para processar mensagens recebidas do stream sem bloquear o callback do gRPC. */
  private final TaskExecutor executor;

  /** Identificador lógico do Receiver (por padrão, hostname do container). */
  private final String receiverId;

  /**
   * Constrói o subscriber do Receiver.
   *
   * @param senderAsync stub assíncrono do Sender para subscribe.
   * @param senderBlocking stub bloqueante do Sender para ACK/NACK.
   * @param server stub bloqueante do Server para processamento.
   * @param results repositório de resultados.
   * @param clock relógio injetável.
   * @param executor executor para processamento.
   */
  public Q3ReceiverSubscriber(
      @GrpcClient("sender") SenderServiceGrpc.SenderServiceStub senderAsync,
      @GrpcClient("sender") SenderServiceGrpc.SenderServiceBlockingStub senderBlocking,
      @GrpcClient("server") ServerServiceGrpc.ServerServiceBlockingStub server,
      ProcessingResultRepository results,
      Clock clock,
      TaskExecutor executor
  ) {
    this.senderAsync = senderAsync;
    this.senderBlocking = senderBlocking;
    this.server = server;
    this.results = results;
    this.clock = clock;
    this.executor = executor;
    this.receiverId = System.getenv().getOrDefault("HOSTNAME", "receiver");
  }

  /**
   * Inicializa a assinatura do stream ao subir o container.
   *
   * @param args argumentos de linha de comando.
   */
  @Override
  public void run(String... args) {
    Thread t = new Thread(this::subscribeLoop, "q3-sender-subscribe");
    t.start();
  }

  /**
   * Mantém a assinatura ativa com retry infinito e backoff.
   *
   * <p>Comportamento: se o stream falhar ou completar, aguarda um pouco e tenta novamente.
   */
  private void subscribeLoop() {
    Duration backoff = Duration.ofSeconds(1);

    while (true) {
      CountDownLatch done = new CountDownLatch(1);
      try {
        senderAsync.subscribe(SubscribeRequest.newBuilder().setReceiverId(receiverId).build(), new StreamObserver<>() {
          @Override
          public void onNext(PushedMessage value) {
            executor.execute(() -> handleMessage(value));
          }

          @Override
          public void onError(Throwable t) {
            log.warn("Q3 erro no stream de assinatura: {}", t.toString());
            done.countDown();
          }

          @Override
          public void onCompleted() {
            log.warn("Q3 stream de assinatura finalizado (Sender encerrou o stream)");
            done.countDown();
          }
        });

        done.await();
      } catch (Exception ex) {
        log.warn("Q3 falha ao assinar (Sender indisponível no momento?): {}", ex.toString());
      }

      try {
        Thread.sleep(backoff.toMillis());
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
        return;
      }
    }
  }

  private void handleMessage(PushedMessage msg) {
    String leaseToken = msg.getLeaseToken();
    UUID messageId;
    try {
      messageId = UUID.fromString(msg.getMessageId());
    } catch (Exception ex) {
      log.warn("Q3 idMensagem inválido recebido: {}", msg.getMessageId());
      return;
    }

    try {
      if (results.existsById(messageId)) {
        boolean acked = senderBlocking.ackMessage(AckMessageRequest.newBuilder()
            .setMessageId(messageId.toString())
            .setLeaseToken(leaseToken)
            .build()).getAcked();
        log.info("Q3 mensagem já processada idMensagem={}, ack={}", messageId, acked);
        return;
      }

      String result = callServerWithRetries(messageId, msg.getPayload());

      results.save(new ProcessingResultEntity(
          messageId,
          MessageChannel.Q3_GRPC,
          Instant.now(clock),
          result
      ));

      boolean acked = senderBlocking.ackMessage(AckMessageRequest.newBuilder()
          .setMessageId(messageId.toString())
          .setLeaseToken(leaseToken)
          .build()).getAcked();

      log.info("Q3 mensagem processada idMensagem={}, resultado={}, ack={}", messageId, result, acked);
    } catch (StatusRuntimeException ex) {
      nackQuietly(messageId, leaseToken, ex.toString());
      log.warn("Q3 falha ao processar idMensagem={}, vai tentar novamente via NACK. erro={}", messageId, ex.toString());
    } catch (Exception ex) {
      nackQuietly(messageId, leaseToken, ex.toString());
      log.warn("Q3 falha ao processar idMensagem={}, vai tentar novamente via NACK. erro={}", messageId, ex.toString());
    }
  }

  /**
   * Chama o Server com retry limitado.
   *
   * <p>Motivo: o Server pode reiniciar/ficar indisponível temporariamente.
   *
   * @param messageId id da mensagem.
   * @param payload conteúdo da mensagem.
   * @return resultado do processamento.
   */
  private String callServerWithRetries(UUID messageId, String payload) {
    int maxAttempts = 3;
    Duration backoff = Duration.ofMillis(500);
    StatusRuntimeException last = null;

    for (int attempt = 1; attempt <= maxAttempts; attempt++) {
      try {
        return server.process(ProcessRequest.newBuilder()
            .setMessageId(messageId.toString())
            .setPayload(payload)
            .build()).getResult();
      } catch (StatusRuntimeException ex) {
        last = ex;
        log.warn("Q3 tentativa de chamada ao Server {}/{} falhou: {}", attempt, maxAttempts, ex.toString());
        try {
          TimeUnit.MILLISECONDS.sleep(backoff.toMillis());
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
          throw ex;
        }
        backoff = backoff.multipliedBy(2);
      }
    }

    throw last;
  }

  private void nackQuietly(UUID messageId, String leaseToken, String error) {
    try {
      senderBlocking.nackMessage(NackMessageRequest.newBuilder()
          .setMessageId(messageId.toString())
          .setLeaseToken(leaseToken)
          .setError(error)
          .build());
    } catch (Exception ignore) {
      // Ignora: o Sender pode estar temporariamente indisponível.
    }
  }
}
