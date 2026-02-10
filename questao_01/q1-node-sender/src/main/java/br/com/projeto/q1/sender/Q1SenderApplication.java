package br.com.projeto.q1.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação Spring Boot do nó Sender (Questão 01).
 *
 * <p>Papel: receber mensagens do ClientApp, persistir no mailbox e expor endpoints de pulling/ACK.
 */
@SpringBootApplication(scanBasePackages = "br.com.projeto")
public class Q1SenderApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q1SenderApplication.class, args);
  }
}
