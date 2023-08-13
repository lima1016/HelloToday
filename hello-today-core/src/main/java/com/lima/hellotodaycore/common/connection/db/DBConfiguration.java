package com.lima.hellotodaycore.common.connection.db;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfiguration {

  @Value("${spring.datasource.driver-class-name}")
  private String className;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String userName;

  @Value("${spring.datasource.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .driverClassName(className)
        .url(url)
        .username(userName)
        .password(password)
        .build();
  }
}