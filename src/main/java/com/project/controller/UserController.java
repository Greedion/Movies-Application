package com.project.controller;

import com.project.model.FullUser;
import com.project.model.User;
import com.project.service.user.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users.", notes = "Needed authorization from Admin account")
    @GetMapping(produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FullUser>> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Create account.")
    @PostMapping(value = "/createaccount", produces = "application/json", consumes = "application/json")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> createAccount(@Valid @RequestBody User user, BindingResult result) {
        if (user == null) {
            logger.error("Attempt create account with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create account with empty input data.");
        } else if (user.getUsername() == null || user.getPassword() == null) {
            logger.error("Attempt create account with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create account with empty input data.");
        } else {
            if(result.hasErrors()) {
                logger.error("Attempt to create account with wrong data structure.");
                return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
            }else{
                return userService.createAccount(user);
            }
        }
    }

    private Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }

}
