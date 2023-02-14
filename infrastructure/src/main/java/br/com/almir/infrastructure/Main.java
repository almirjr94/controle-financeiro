package br.com.almir.infrastructure;

import br.com.almir.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
    SpringApplication.run(WebServerConfig.class, args);
  }
}