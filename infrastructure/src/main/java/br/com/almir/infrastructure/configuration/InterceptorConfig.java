package br.com.almir.infrastructure.configuration;

import br.com.almir.infrastructure.api.interceptor.HttpInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfig implements WebMvcConfigurer {

  private final HttpInterceptor inperceptor;

  public InterceptorConfig(HttpInterceptor inperceptor) {
    this.inperceptor = inperceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(inperceptor);
  }

}
