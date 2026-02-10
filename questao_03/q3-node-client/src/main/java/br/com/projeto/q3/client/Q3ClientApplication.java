package br.com.projeto.q3.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação Spring Boot do nó Client (Questão 03).
 *
 * <p>Papel: iniciar o container do cliente e executar o envio via {@link Q3ClientRunner}.
 */
@SpringBootApplication(scanBasePackages = "br.com.projeto")
public class Q3ClientApplication {

  /**
   * Entry-point da aplicação.
   *
   * @param args argumentos de linha de comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(Q3ClientApplication.class, args);
  }
}
