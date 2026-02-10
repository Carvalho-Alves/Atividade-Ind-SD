package br.com.projeto.shared.config;

import java.time.Clock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Beans de infraestrutura compartilhados dentro do nó.
 *
 * <p>Este arquivo existe dentro do nó (e não em um módulo shared único) para manter o
 * isolamento exigido pelo enunciado.
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppWiring {

  /** @return relógio do sistema em UTC, para timestamps consistentes. */
  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  /**
   * Executor usado para tarefas assíncronas internas do nó.
   *
   * @return executor para tarefas assíncronas.
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
