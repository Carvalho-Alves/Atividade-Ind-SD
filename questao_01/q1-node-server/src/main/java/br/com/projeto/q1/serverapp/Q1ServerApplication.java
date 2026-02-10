package br.com.projeto.q1.serverapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação Spring Boot do nó ServerApp (Questão 01).
 *
 * <p>Papel: expor um endpoint de processamento de mensagens encaminhadas pelo Receiver.
 */
@SpringBootApplication(scanBasePackages = "br.com.projeto")
public class Q1ServerApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q1ServerApplication.class, args);
  }
}
