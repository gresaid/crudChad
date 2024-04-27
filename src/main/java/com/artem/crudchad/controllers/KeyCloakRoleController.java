package com.artem.crudchad.controllers;

import com.artem.crudchad.dto.auth.UserRegistrationRequest;
import com.artem.crudchad.service.keycloak.KeyCloakUserService;
import com.artem.crudchad.service.keycloak.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class KeyCloakRoleController {

  private final KeyCloakUserService keyCloakUserService;
  private final RoleService roleService;

  @PostMapping
  public UserRegistrationRequest createUser(
      @RequestBody UserRegistrationRequest userRegistrationRequest) {
    return keyCloakUserService.createUser(userRegistrationRequest);
  }

  @PutMapping("/assign-role/user/{userId}")
  public ResponseEntity<?> assignRole(@PathVariable String userId, @RequestParam String roleName) {
    roleService.assignRole(userId, roleName);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
