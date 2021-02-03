package com.Movies.Controller;

import com.Movies.POJO.POJOUser;
import com.Movies.Security.JWTAuth.JWTUtils;
import com.Movies.Service.Authorization.AuthorizationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final JWTUtils jwtUtils;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthorizationServiceImpl authorizationService;

    public AuthController(AuthorizationServiceImpl authorizationService,
                          AuthenticationManager authenticationManager,
                          JWTUtils jwtUtils) {
        this.authorizationService = authorizationService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> login(@RequestBody POJOUser pojoUser) {
        if(pojoUser==null){
            logger.error("Attempt login with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt login with empty input data");
        } else if(pojoUser.getUsername() == null || pojoUser.getPassword() == null){
            logger.error("Attempt login with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt login with empty input data");
        }else {
            return authorizationService.start(pojoUser, authenticationManager);
        }
    }

}