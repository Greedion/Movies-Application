package com.Movies.Controller;

import com.Movies.POJO.POJOUser;
import com.Movies.Security.JWTAuth.JWTUtils;
import com.Movies.Service.Authorization.AuthorizationServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    @ApiOperation(value = "Log in.", notes = "Default account's : Admin/Admin User/User")
    @PostMapping("/signin")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> login(@Valid @RequestBody POJOUser pojoUser, BindingResult result) {
        if(pojoUser==null){
            logger.error("Attempt login with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt login with empty input data");
        } else if(pojoUser.getUsername() == null || pojoUser.getPassword() == null){
            logger.error("Attempt login with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt login with empty input data");
        }else if (result.hasErrors()) {
            logger.error("Attempt to sing in with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        else {
            return authorizationService.start(pojoUser, authenticationManager);
        }
    }

    Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }

}