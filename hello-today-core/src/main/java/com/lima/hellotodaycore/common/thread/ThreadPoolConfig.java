package com.lima.hellotodaycore.common.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

  private static class ThreadPoolSingleTon {
    private static final ThreadPoolConfig THREAD_POOL_SINGLETON = new ThreadPoolConfig();
  }

  public static ThreadPoolConfig getInstance() {
    return ThreadPoolSingleTon.THREAD_POOL_SINGLETON;
  }

  @Bean
  public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(25);
    executor.initialize();
    return executor;
  }
}