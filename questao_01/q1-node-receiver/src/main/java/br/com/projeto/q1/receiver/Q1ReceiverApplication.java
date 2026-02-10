package br.com.projeto.q1.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicação Spring Boot do nó Receiver (Questão 01).
 *
 * <p>Papel: realizar polling no Sender, encaminhar para o ServerApp e emitir ACK.
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = "br.com.projeto")
public class Q1ReceiverApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q1ReceiverApplication.class, args);
  }
}
