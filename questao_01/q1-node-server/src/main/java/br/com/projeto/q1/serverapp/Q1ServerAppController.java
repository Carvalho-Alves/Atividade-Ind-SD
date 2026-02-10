package br.com.projeto.q1.serverapp;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ServerApp (Questao 1).
 *
 * Papel: processar mensagens encaminhadas pelo Receiver.
 *
 * Observacao:
 * - Aqui mantemos o processamento simples (apenas retorna um "resultado").
 * - Na Questao 3, este componente evolui para idempotencia e resiliencia.
 */
@RestController
@Profile("serverapp")
@RequestMapping(path = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
public class Q1ServerAppController {

  /**
   * Processa uma mensagem encaminhada pelo Receiver.
   *
   * <p>Comportamento: simula uma lógica de negócio simples e retorna um resultado determinístico
   * baseado no payload e no instante atual.
   *
   * <p>Observação: nesta questão o ServerApp é stateless e não persiste efeitos. Já na Q3 ele
   * persiste resultados para garantir idempotência.
   *
   * @param request request contendo id e payload.
   * @return response com resultado.
   */
  @PostMapping(path = "/process", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProcessResponse> process(@RequestBody ProcessRequest request) {
    String result = "processado=" + request.payload() + ", em=" + Instant.now();
    return ResponseEntity.ok(new ProcessResponse(request.messageId(), result));
  }

  /**
   * DTO de entrada do ServerApp (Q1).
   *
   * @param messageId id da mensagem (rastreabilidade/correlação).
   * @param payload conteúdo a ser processado.
   */
  public record ProcessRequest(UUID messageId, @NotBlank String payload) {}

  /**
   * DTO de saída do ServerApp (Q1).
   *
   * @param messageId id da mensagem.
   * @param result resultado textual do processamento.
   */
  public record ProcessResponse(UUID messageId, String result) {}
}
