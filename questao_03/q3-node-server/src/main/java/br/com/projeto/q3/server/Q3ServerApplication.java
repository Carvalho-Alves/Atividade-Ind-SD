package br.com.projeto.q3.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação Spring Boot do nó Server (Questão 03).
 *
 * <p>Papel: expor o serviço gRPC de processamento de mensagens (Receiver -> Server).
 */
@SpringBootApplication(scanBasePackages = "br.com.projeto")
public class Q3ServerApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q3ServerApplication.class, args);
  }
}
