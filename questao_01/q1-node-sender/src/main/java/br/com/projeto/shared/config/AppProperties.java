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

  /**
   * Conjunto de configurações relacionadas a leasing/visibility-timeout.
   *
   * <p>Estado: esta instância é mutável via binding do Spring ({@code application.yml}).
   */
  private final Lease lease = new Lease();

  /**
   * Conjunto de configurações relacionadas ao comportamento do Receiver (polling).
   *
   * <p>Estado: esta instância é mutável via binding do Spring ({@code application.yml}).
   */
  private final Receiver receiver = new Receiver();

  /**
   * Retorna as propriedades de leasing.
   *
   * @return configurações de lease (lockDuration, maxAttempts).
   */
  public Lease getLease() {
    return lease;
  }

  /**
   * Retorna as propriedades do Receiver.
   *
   * @return configurações do Receiver (intervalo de polling etc.).
   */
  public Receiver getReceiver() {
    return receiver;
  }

  /** Configuracoes de "visibility timeout" (lease) e tentativas. */
  public static class Lease {

    /**
     * Por quanto tempo uma mensagem fica reservada para um Receiver.
     * Se o Receiver morrer, ao expirar o lease outro Receiver pode tentar novamente.
     */
    private Duration lockDuration = Duration.ofSeconds(30);

    /**
     * Limite de tentativas antes de marcar a mensagem como DEAD.
     * Isso evita loops infinitos em mensagens que sempre falham.
     */
    private int maxAttempts = 10;

    /**
     * Duração do lease/lock.
     *
     * <p>Comportamento: enquanto o lease estiver válido, a mensagem é considerada “em voo”
     * e não deve ser entregue a outro Receiver.
     *
     * @return duração atual configurada.
     */
    public Duration getLockDuration() {
      return lockDuration;
    }

    /**
     * Define a duração do lease/lock.
     *
     * @param lockDuration nova duração do lease.
     */
    public void setLockDuration(Duration lockDuration) {
      this.lockDuration = lockDuration;
    }

    /**
     * Número máximo de tentativas de entrega/processamento.
     *
     * <p>Comportamento: ao exceder este limite, a mensagem pode ser marcada como DEAD (DLQ simplificada).
     *
     * @return número máximo de tentativas.
     */
    public int getMaxAttempts() {
      return maxAttempts;
    }

    /**
     * Define o número máximo de tentativas.
     *
     * @param maxAttempts limite de tentativas antes de DEAD.
     */
    public void setMaxAttempts(int maxAttempts) {
      this.maxAttempts = maxAttempts;
    }
  }

  /** Intervalos e parametros do Receiver (pulling). */
  public static class Receiver {

    /** Intervalo entre tentativas de pulling automatico. */
    private Duration pollInterval = Duration.ofSeconds(2);

    /**
     * Intervalo entre execuções do polling.
     *
     * <p>Comportamento: controla a latência média de entrega (menor intervalo = mais rápido,
     * porém com maior carga de chamadas).
     *
     * @return duração do intervalo de polling.
     */
    public Duration getPollInterval() {
      return pollInterval;
    }

    /**
     * Define o intervalo do polling.
     *
     * @param pollInterval nova duração do intervalo.
     */
    public void setPollInterval(Duration pollInterval) {
      this.pollInterval = pollInterval;
    }
  }
}
