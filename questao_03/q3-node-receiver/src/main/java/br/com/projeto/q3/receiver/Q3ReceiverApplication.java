package br.com.projeto.q3.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicação Spring Boot do nó Receiver (Questão 03).
 *
 * <p>Papel: iniciar o container do Receiver e executar a assinatura do stream do Sender.
 */
@SpringBootApplication(scanBasePackages = "br.com.projeto")
@EntityScan(basePackages = "br.com.projeto")
@EnableJpaRepositories(basePackages = "br.com.projeto")
public class Q3ReceiverApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q3ReceiverApplication.class, args);
  }
}
