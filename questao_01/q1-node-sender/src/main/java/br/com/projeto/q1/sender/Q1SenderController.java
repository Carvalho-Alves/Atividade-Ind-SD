package br.com.projeto.q1.sender;

import br.com.projeto.shared.messaging.LeasedMessage;
import br.com.projeto.shared.messaging.MessageChannel;
import br.com.projeto.shared.messaging.MessageStore;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sender (Questao 1).
 *
 * Responsabilidades:
 * - Receber mensagens do ClientApp ("dispare-e-esqueça") e armazenar no mailbox.
 * - Expor endpoint de pulling (try delivery) para o Receiver.
 * - Confirmar ACK para remover/confirmar a entrega.
 */
@RestController
@Profile("sender")
@RequestMapping(path = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
public class Q1SenderController {

  /**
   * Abstração do mailbox.
   *
   * <p>Papel: persistir mensagens e controlar leasing/ACK/NACK.
   * Estado: aponta para uma implementação baseada em Postgres (via Spring DI).
   */
  private final MessageStore store;

  /**
   * Constrói o Sender da Questão 1.
   *
   * @param store implementação do mailbox.
   */
  public Q1SenderController(MessageStore store) {
    this.store = store;
  }

  /**
    * ClientApp -> Sender (não bloqueante / "dispare-e-esqueça").
   */
  @PostMapping(path = "/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SendMessageResponse> send(@RequestBody SendMessageRequest request) {
    UUID id = store.enqueue(MessageChannel.Q1_REST, request.payload(), null);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SendMessageResponse(id));
  }

  /**
   * Receiver -> Sender (pulling / try delivery).
   */
  @GetMapping(path = "/messages/try-deliver")
  public ResponseEntity<TryDeliverResponse> tryDeliver(@RequestParam("receiverId") @NotBlank String receiverId) {
    Optional<LeasedMessage> leased = store.tryDeliver(MessageChannel.Q1_REST, receiverId);

    return leased
        .map(msg -> ResponseEntity.ok(new TryDeliverResponse(
            true,
            msg.id(),
            msg.payload(),
            msg.attemptCount(),
            msg.lockOwner(),
            msg.lockedUntil().toString()
        )))
        .orElseGet(() -> ResponseEntity.ok(new TryDeliverResponse(false, null, null, 0, null, null)));
  }

  /**
   * Receiver -> Sender (ACK).
   */
  @PostMapping(path = "/messages/{id}/ack")
  public ResponseEntity<AckResponse> ack(
      @PathVariable("id") UUID messageId,
      @RequestParam("receiverId") @NotBlank String receiverId
  ) {
    boolean ok = store.ack(messageId, receiverId);
    return ResponseEntity.ok(new AckResponse(ok));
  }

  /**
   * DTO de entrada para publicação no mailbox (Q1).
   *
   * @param payload conteúdo textual a ser armazenado no Sender.
   */
  public record SendMessageRequest(@NotBlank String payload) {}

  /**
   * DTO de saída para publicação (Q1).
   *
   * @param messageId id atribuído à mensagem no mailbox.
   */
  public record SendMessageResponse(UUID messageId) {}

  /**
   * DTO de saída do endpoint de try-deliver.
   *
   * <p>Comportamento:
   * - Se {@code found=false}, os demais campos ficam nulos/zero.
   * - Se {@code found=true}, os campos descrevem a mensagem leased.
   *
   * @param found indica se uma mensagem foi encontrada e reservada.
   * @param messageId id da mensagem.
   * @param payload conteúdo.
   * @param attemptCount número da tentativa (após incrementar).
   * @param lockOwner receiver dono do lease.
   * @param lockedUntil timestamp de expiração do lease.
   */
  public record TryDeliverResponse(
      boolean found,
      UUID messageId,
      String payload,
      int attemptCount,
      String lockOwner,
      String lockedUntil
  ) {}

  /**
   * DTO de saída do ACK.
   *
   * @param acked indica se o ACK foi efetivamente aplicado.
   */
  public record AckResponse(boolean acked) {}
}
