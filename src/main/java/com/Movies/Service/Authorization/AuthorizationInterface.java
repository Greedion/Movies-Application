package com.Movies.Service.Authorization;

import com.Movies.POJO.POJOUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthorizationInterface {

     ResponseEntity<?> start(POJOUser pojoUser, AuthenticationManager authenticationManager);
}
