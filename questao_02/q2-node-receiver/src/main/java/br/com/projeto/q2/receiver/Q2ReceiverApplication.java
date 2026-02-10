package br.com.projeto.q2.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicação Spring Boot do nó Receiver (Questão 02).
 *
 * <p>Papel: executar polling (pulling) no Sender, processar no Server e finalizar com ACK/NACK.
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = "br.com.projeto")
@EntityScan(basePackages = "br.com.projeto")
@EnableJpaRepositories(basePackages = "br.com.projeto")
public class Q2ReceiverApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q2ReceiverApplication.class, args);
  }
}
