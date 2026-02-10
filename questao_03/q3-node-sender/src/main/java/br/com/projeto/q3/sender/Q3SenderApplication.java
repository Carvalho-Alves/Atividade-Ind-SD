package br.com.projeto.q3.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Aplicação Spring Boot do nó Sender (Questão 03).
 *
 * <p>Papel: expor o serviço gRPC do Sender e persistir mensagens no mailbox (Postgres).
 */
@SpringBootApplication(scanBasePackages = "br.com.projeto")
@EntityScan(basePackages = "br.com.projeto")
@EnableJpaRepositories(basePackages = "br.com.projeto")
public class Q3SenderApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q3SenderApplication.class, args);
  }
}
