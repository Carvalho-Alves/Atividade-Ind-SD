package br.com.projeto.q1.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClientApp (Questao 1).
 *
 * Papel: simular um cliente que envia mensagem para o Sender de forma nao bloqueante
 * ("dispare-e-esqueça"). O endpoint retorna 202 imediatamente.
 */
@RestController
@Profile("client")
@RequestMapping(path = "/q1/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientAppController {

  /**
   * Cliente HTTP utilizado para disparar a chamada assíncrona ao Sender.
   *
    * <p>Observação: o envio é feito em background ("dispare-e-esqueça"), portanto este client não
   * bloqueia o request do endpoint.
   */
  private final HttpClient httpClient = HttpClient.newHttpClient();

  /**
   * URI completa do endpoint de publicação no Sender.
   *
   * <p>Exemplo em Docker: {@code http://sender:8080/q1/messages}.
   */
  private final URI senderUri;

  /**
   * Constrói o controller do ClientApp.
   *
   * @param senderBaseUrl base URL do Sender (ex.: http://sender:8080 em Docker).
   */
  public ClientAppController(@Value("${app.sender.base-url}") String senderBaseUrl) {
    this.senderUri = URI.create(senderBaseUrl + "/q1/messages");
  }

  /**
    * Dispara uma requisição HTTP assíncrona ao Sender e retorna sem esperar o resultado.
    *
    * <p>Comportamento: este endpoint sempre retorna {@code 202 Accepted} se conseguiu disparar
    * a chamada em background (sem aguardar resposta do Sender).
    *
    * @param request conteúdo a ser enfileirado no Sender.
    * @return resposta imediata indicando que o envio foi disparado.
   */
  @PostMapping(path = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SendAndForgetResponse> sendAndForget(@RequestBody SendAndForgetRequest request) {
    String jsonPayload = "{\"payload\":\"" + escapeJson(request.payload()) + "\"}";

    HttpRequest httpRequest = HttpRequest.newBuilder(senderUri)
        .timeout(Duration.ofSeconds(3))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
        .build();

    // Dispara em background; o retorno do ClientApp nao depende do resultado.
    httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.discarding());

    return ResponseEntity.status(HttpStatus.ACCEPTED)
      .body(new SendAndForgetResponse("ENVIADO"));
  }

  /**
   * Faz escaping mínimo para embutir o payload em um JSON simples.
   *
   * <p>Comportamento: escapa barra invertida e aspas duplas. É suficiente para o laboratório,
   * evitando trazer uma dependência extra apenas para montar um JSON muito pequeno.
   *
   * @param value texto original.
   * @return texto com caracteres escapados para JSON.
   */
  private static String escapeJson(String value) {
    return value.replace("\\", "\\\\").replace("\"", "\\\"");
  }

  /**
   * DTO de entrada do endpoint do ClientApp (Q1).
   *
   * @param payload conteúdo textual a ser enfileirado no Sender.
   */
  public record SendAndForgetRequest(String payload) {}

  /**
   * DTO de saída do endpoint do ClientApp (Q1).
   *
   * @param status status lógico da operação (ex.: ENVIADO).
   */
  public record SendAndForgetResponse(String status) {}
}
