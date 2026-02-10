package br.com.projeto.q3.client;

import br.com.projeto.q3.proto.sender.SenderServiceGrpc;
import br.com.projeto.q3.proto.sender.SendMessageRequest;
import io.grpc.StatusRuntimeException;
import java.time.Duration;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Runner do nó Client (Questão 03).
 *
 * <p>Papel: enviar uma mensagem via gRPC para o Sender ao subir o container.
 * Inclui retry simples para tolerar inicialização fora de ordem no Docker Compose.
 */
@Component
public class Q3ClientRunner implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(Q3ClientRunner.class);

  /** Stub gRPC de acesso ao Sender. */
  private final SenderServiceGrpc.SenderServiceBlockingStub sender;

  /** Payload configurável que será enviado ao Sender. */
  private final String payload;

  /**
   * Constrói o runner do Client.
   *
   * @param sender stub gRPC do Sender.
   * @param payload payload configurável.
   */
  public Q3ClientRunner(
      @GrpcClient("sender") SenderServiceGrpc.SenderServiceBlockingStub sender,
      @Value("${app.client.payload:hello-q3}") String payload
  ) {
    this.sender = sender;
    this.payload = payload;
  }

  /**
   * Executa o envio inicial com tentativas e backoff.
   *
   * @param args argumentos de linha de comando.
   */
  @Override
  public void run(String... args) {
    int maxAttempts = 15;
    Duration backoff = Duration.ofSeconds(1);

    for (int attempt = 1; attempt <= maxAttempts; attempt++) {
      try {
        var response = sender.sendMessage(SendMessageRequest.newBuilder().setPayload(payload).build());
        log.info("Q3 mensagem enviada idMensagem={}, payload={}", response.getMessageId(), payload);
        return;
      } catch (StatusRuntimeException ex) {
        log.warn("Q3 tentativa de envio {}/{} falhou: {}", attempt, maxAttempts, ex.toString());
        try {
          Thread.sleep(backoff.toMillis());
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
          return;
        }
      }
    }

    log.error("Q3 não conseguiu enviar a mensagem após {} tentativas; desistindo.", maxAttempts);
  }
}
