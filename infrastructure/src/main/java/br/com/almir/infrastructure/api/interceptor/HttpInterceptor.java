package br.com.almir.infrastructure.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpInterceptor implements HandlerInterceptor {

  @Value("${api.key:1234}")
  private String apiKey;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String path = request.getRequestURI().substring(request.getContextPath().length());
    if (path.startsWith("/swagger-ui/")) return true;
    if (path.startsWith("/v3/api-docs")) return true;
    if (path.startsWith("/actuator/")) return true;


    if (request.getHeader("api-key") != null &&
        request.getHeader("api-key").equals(apiKey)) {
      return true;
    }

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("401 Unauthorized");
    response.getWriter().flush();
    return false;


  }
}
