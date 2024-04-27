package com.artem.crudchad.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

  @Value("${app.keycloak.client-id}")
  private String clientId;
  @Value("${app.keycloak.credentials.secret}")
  private String clientSecret;
  @Value("${app.keycloak.auth-server-url}")
  private String authServerUrl;
  @Value("${app.keycloak.realm}")
  private String realm;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(authServerUrl)
        .realm(realm)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .build();

  }
}
