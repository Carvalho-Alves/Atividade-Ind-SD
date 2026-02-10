package br.com.projeto.shared.config;

import java.time.Clock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Wiring de beans compartilhados.
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppWiring {

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

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
