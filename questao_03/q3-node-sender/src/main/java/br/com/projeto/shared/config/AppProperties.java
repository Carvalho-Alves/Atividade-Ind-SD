package br.com.projeto.shared.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriedades da aplicacao.
 *
 * Objetivo: manter configuracoes (lease, polling, limites) centralizadas e tipadas,
 * deixando o codigo mais limpo e com menos "strings magicas".
 */
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private final Lease lease = new Lease();

  private final Receiver receiver = new Receiver();

  public Lease getLease() {
    return lease;
  }

  public Receiver getReceiver() {
    return receiver;
  }

  /** Configuracoes de "visibility timeout" (lease) e tentativas. */
  public static class Lease {

    private Duration lockDuration = Duration.ofSeconds(30);

    private int maxAttempts = 10;

    public Duration getLockDuration() {
      return lockDuration;
    }

    public void setLockDuration(Duration lockDuration) {
      this.lockDuration = lockDuration;
    }

    public int getMaxAttempts() {
      return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
      this.maxAttempts = maxAttempts;
    }
  }

  /** Intervalos e parametros do Receiver (pulling). */
  public static class Receiver {

    private Duration pollInterval = Duration.ofSeconds(2);

    public Duration getPollInterval() {
      return pollInterval;
    }

    public void setPollInterval(Duration pollInterval) {
      this.pollInterval = pollInterval;
    }
  }
}
