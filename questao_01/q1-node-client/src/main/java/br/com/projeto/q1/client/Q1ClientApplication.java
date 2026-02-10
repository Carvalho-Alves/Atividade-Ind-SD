package br.com.projeto.q1.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação Spring Boot do nó ClientApp (Questão 01).
 *
 * <p>Papel: expor um endpoint HTTP que dispara uma chamada "dispare-e-esqueça" para o Sender.
 */
@SpringBootApplication(scanBasePackages = "br.com.projeto")
public class Q1ClientApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q1ClientApplication.class, args);
  }
}
