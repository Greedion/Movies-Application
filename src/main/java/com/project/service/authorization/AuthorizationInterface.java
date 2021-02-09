package com.project.service.authorization;

import com.project.model.JWTResponse;
import com.project.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthorizationInterface {

     ResponseEntity<JWTResponse> start(User user, AuthenticationManager authenticationManager);
}
