package com.artem.crudchad.config.security;

import java.util.stream.Stream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(configurer -> configurer.anyRequest()
            .permitAll())
        .sessionManagement(
            configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(CsrfConfigurer::disable)
        .oauth2ResourceServer(configurer -> configurer.jwt(jwt -> {
          JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
          jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");
          jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);

          JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

          JwtGrantedAuthoritiesConverter customJwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
          customJwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
          customJwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

          jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(token ->
              Stream.concat(jwtGrantedAuthoritiesConverter.convert(token).stream(),
                      customJwtGrantedAuthoritiesConverter.convert(token).stream())
                  .toList());
        }))
        .build();
  }
}
