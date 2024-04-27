package com.artem.crudchad.dto.auth;

public record UserRegistrationRequest(String username, String email, String firstName,
                                      String lastName, String password) {

}