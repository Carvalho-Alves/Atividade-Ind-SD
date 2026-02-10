package br.com.projeto.q1.receiver;

import br.com.projeto.shared.config.AppProperties;
import br.com.projeto.shared.messaging.MessageStore;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Receiver (Questao 1) - Pulling scheduler.
 *
 * Responsabilidade:
 * - Periodicamente buscar mensagens do Sender via "try delivery".
 * - Encaminhar para o ServerApp de forma blocking (forward).
 * - Ao sucesso, emitir ACK ao Sender.
 *
 * Detalhe importante de comunicacao indireta:
 * - O Receiver nao recebe push do Sender. Ele faz pulling.
 */
@Component
@Profile("receiver")
public class Q1ReceiverPoller {

  private static final Logger log = LoggerFactory.getLogger(Q1ReceiverPoller.class);

  /**
   * Cliente REST para conversar com o Sender.
   *
   * <p>Estado: configurado com {@code app.sender.base-url}.
   */
  private final RestClient senderClient;

  /**
   * Cliente REST para encaminhar chamadas ao ServerApp (processamento).
   *
   * <p>Estado: configurado com {@code app.serverapp.base-url}.
   */
  private final RestClient serverAppClient;

  /**
   * Abstração do mailbox para operações de NACK (retry) em caso de falha.
   *
   * <p>Observação: aqui usamos o store diretamente para liberar retry sem depender do Sender REST.
   */
  private final MessageStore store;

  /**
   * Propriedades de aplicação (polling/lease).
   *
   * <p>Estado: usado para parametrização do comportamento do Receiver.
   */
  private final AppProperties properties;

  /**
   * Identificador lógico do Receiver (usado em leasing/ACK).
   *
   * <p>Estado: por padrão usa o hostname do container.
   */
  private final String receiverId;

  /**
   * Constrói o poller do Receiver (Q1).
   *
   * @param store mailbox (para NACK/retry).
   * @param properties propriedades tipadas da aplicação.
   * @param senderBaseUrl base URL do Sender.
   * @param serverAppBaseUrl base URL do ServerApp.
   * @param receiverId id lógico do receiver.
   */
  public Q1ReceiverPoller(
      MessageStore store,
      AppProperties properties,
      @Value("${app.sender.base-url}") String senderBaseUrl,
      @Value("${app.serverapp.base-url}") String serverAppBaseUrl,
      @Value("${HOSTNAME:receiver}") String receiverId
  ) {
    this.store = store;
    this.properties = properties;
    this.receiverId = receiverId;
    this.senderClient = RestClient.builder().baseUrl(senderBaseUrl).build();
    this.serverAppClient = RestClient.builder().baseUrl(serverAppBaseUrl).build();
  }

  /**
   * Execucao ciclica do pulling.
   *
   * Usamos SpEL para converter Duration -> millis.
   */
  @Scheduled(fixedDelayString = "${app.receiver.poll-interval:2s}")
  public void pollOnce() {
    TryDeliverResponse delivery = senderClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/q1/messages/try-deliver")
            .queryParam("receiverId", receiverId)
            .build())
        .retrieve()
        .body(TryDeliverResponse.class);

    if (delivery == null || !delivery.found() || delivery.messageId() == null) {
      return;
    }

    UUID messageId = delivery.messageId();

    try {
      // Forward blocking para o ServerApp.
      Q1ProcessResponse processResponse = serverAppClient.post()
          .uri("/q1/process")
          .contentType(MediaType.APPLICATION_JSON)
          .body(new Q1ProcessRequest(messageId, delivery.payload()))
          .retrieve()
          .body(Q1ProcessResponse.class);

      // ACK no Sender.
      AckResponse ack = senderClient.post()
          .uri(uriBuilder -> uriBuilder
              .path("/q1/messages/{id}/ack")
              .queryParam("receiverId", receiverId)
              .build(messageId))
          .retrieve()
          .body(AckResponse.class);

      log.info(
          "Q1 mensagem entregue idMensagem={}, resultadoProcessamento={}, ack={} ",
          messageId,
          (processResponse != null ? processResponse.result() : null),
          (ack != null && ack.acked())
      );
    } catch (Exception ex) {
      // Em caso de falha no forward/processing, liberamos a mensagem para retry.
      store.nack(messageId, receiverId, ex.getMessage());
      log.warn("Q1 falha ao processar idMensagem={}, vai tentar novamente. erro={}", messageId, ex.toString());
    }
  }

  /**
   * DTO (espelho do Sender) para a resposta de try-deliver.
   *
   * <p>Papel: desacoplar o Receiver do pacote do Sender (evita dependência direta de tipos internos).
   *
   * @param found se o Sender encontrou e reservou uma mensagem.
   * @param messageId id da mensagem leased.
   * @param payload conteúdo da mensagem.
   */
  public record TryDeliverResponse(boolean found, UUID messageId, String payload) {}

  /**
   * DTO de request para o ServerApp (processamento da Q1).
   *
   * @param messageId id da mensagem (para rastreabilidade).
   * @param payload conteúdo.
   */
  public record Q1ProcessRequest(UUID messageId, String payload) {}

  /**
   * DTO de response do ServerApp (Q1).
   *
   * @param messageId id da mensagem.
   * @param result resultado textual do processamento.
   */
  public record Q1ProcessResponse(UUID messageId, String result) {}

  /**
   * DTO de response de ACK do Sender.
   *
   * @param acked indica se o ACK foi aplicado.
   */
  public record AckResponse(boolean acked) {}
}
