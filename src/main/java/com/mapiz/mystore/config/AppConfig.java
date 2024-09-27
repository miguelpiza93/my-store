package com.mapiz.mystore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppConfig {

  @Value("${cors.allowed-origins}")
  private String allowedOrigins;

  @Value("${cors.allowed-methods}")
  private String allowedMethods;

  @Value("${cors.allowed-headers}")
  private String allowedHeaders;

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();

    // Allow requests from configured origins
    config.addAllowedOrigin(allowedOrigins);

    // Allow specified methods
    config.addAllowedMethod(allowedMethods);

    // Allow specified headers
    config.addAllowedHeader(allowedHeaders);

    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
