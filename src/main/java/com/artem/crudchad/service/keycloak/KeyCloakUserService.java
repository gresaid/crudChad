package com.artem.crudchad.service.keycloak;

import com.artem.crudchad.dto.auth.UserRegistrationRequest;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeyCloakUserService {

  @Value("${app.keycloak.realm}")
  private String realm;
  private Keycloak keycloak;

  public KeyCloakUserService(Keycloak keycloak) {
    this.keycloak = keycloak;
  }

  public UserRegistrationRequest createUser(UserRegistrationRequest userRegistrationRequest) {
    UserRepresentation user = new UserRepresentation();
    user.setEnabled(true);
    user.setUsername(userRegistrationRequest.username());
    user.setEmail(userRegistrationRequest.email());
    user.setFirstName(userRegistrationRequest.firstName());
    user.setLastName(userRegistrationRequest.lastName());
    user.setEmailVerified(true);
    CredentialRepresentation representation = new CredentialRepresentation();
    representation.setValue(userRegistrationRequest.password());
    representation.setTemporary(false);
    representation.setType(CredentialRepresentation.PASSWORD);

    UsersResource usersResource = getUsersResource();

    Response response = usersResource.create(user);

    if (Objects.equals(201, response.getStatus())) {

      List<UserRepresentation> representationList = usersResource.searchByUsername(
          userRegistrationRequest.username(), true);
      if (!CollectionUtils.isEmpty(representationList)) {
        UserRepresentation userRepresentation1 = representationList.stream().filter(
                userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified()))
            .findFirst().orElse(null);
        assert userRepresentation1 != null;
        emailVerification(userRepresentation1.getId());
      }
      return userRegistrationRequest;


    }
return null;

  }

  private UsersResource getUsersResource() {
    RealmResource realm1 = keycloak.realm(realm);
    return realm1.users();
  }

  public void emailVerification(String userId) {

    UsersResource usersResource = getUsersResource();
    usersResource.get(userId).sendVerifyEmail();
  }
  public UserResource getUserResource(String userId){
    UsersResource usersResource = getUsersResource();
    return usersResource.get(userId);
  }

}
