package br.com.projeto.shared.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriedades de configuração da aplicação.
 *
 * <p>Mapeia o prefixo {@code app.*} dos arquivos de configuração (ex.: YAML) para um objeto Java.
 * Essas propriedades são usadas pelos componentes de mensageria (lease, tentativas e polling).
 */
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  /** Configurações relacionadas ao lease (posse temporária) de mensagens. */
  private final Lease lease = new Lease();

  /** Configurações específicas do receptor (intervalo de polling, etc.). */
  private final Receiver receiver = new Receiver();

  /** @return configurações de lease. */
  public Lease getLease() {
    return lease;
  }

  /** @return configurações do receptor. */
  public Receiver getReceiver() {
    return receiver;
  }

  /**
   * Configurações de lease.
   *
   * <p>Controla por quanto tempo uma mensagem fica "alugada" para um receptor e quantas
   * tentativas máximas são permitidas antes de mover para DEAD.
   */
  public static class Lease {
    /** Duração do lock/lease da mensagem. */
    private Duration lockDuration = Duration.ofSeconds(30);

    /** Número máximo de tentativas de entrega/processamento. */
    private int maxAttempts = 10;

    /** @return duração do lease/lock. */
    public Duration getLockDuration() {
      return lockDuration;
    }

    /** @param lockDuration nova duração do lease/lock. */
    public void setLockDuration(Duration lockDuration) {
      this.lockDuration = lockDuration;
    }

    /** @return máximo de tentativas permitidas. */
    public int getMaxAttempts() {
      return maxAttempts;
    }

    /** @param maxAttempts novo máximo de tentativas permitido. */
    public void setMaxAttempts(int maxAttempts) {
      this.maxAttempts = maxAttempts;
    }
  }

  /** Configurações do receptor (polling). */
  public static class Receiver {
    /** Intervalo entre tentativas de polling no Sender. */
    private Duration pollInterval = Duration.ofSeconds(2);

    /** @return intervalo de polling. */
    public Duration getPollInterval() {
      return pollInterval;
    }

    /** @param pollInterval novo intervalo de polling. */
    public void setPollInterval(Duration pollInterval) {
      this.pollInterval = pollInterval;
    }
  }
}
