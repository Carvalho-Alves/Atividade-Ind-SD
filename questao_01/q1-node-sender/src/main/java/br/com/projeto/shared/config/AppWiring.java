package br.com.projeto.shared.config;

import java.time.Clock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Configuração de beans de infraestrutura compartilhados entre os nós da Questão 01.
 *
 * <p>Papel: centralizar dependências transversais (ex.: {@link Clock} e {@link TaskExecutor})
 * para manter os componentes de negócio mais simples.
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppWiring {

  /**
   * Clock injetavel: ajuda testes e reduz acoplamento a Instant.now() espalhado.
   *
   * @return clock do sistema em UTC.
   */
  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  /**
    * Executor usado para operações "dispare-e-esqueça" (ex.: ClientApp na Q1).
   *
   * @return executor para tarefas assíncronas da aplicação.
   */
  @Bean
  public TaskExecutor applicationTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(4);
    executor.setMaxPoolSize(16);
    executor.setQueueCapacity(1_000);
    executor.setThreadNamePrefix("app-async-");
    executor.initialize();
    return executor;
  }
}
