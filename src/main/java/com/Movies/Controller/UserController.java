package com.Movies.Controller;

import com.Movies.DataTransferObject.UserDTO;
import com.Movies.POJO.POJOUser;
import com.Movies.Service.User.UserServiceImpl;
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
    ResponseEntity<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Create account.")
    @PostMapping(value = "/createaccount", produces = "application/json", consumes = "application/json")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> createAccount(@Valid @RequestBody POJOUser pojoUser, BindingResult result) {
        if (pojoUser == null) {
            logger.error("Attempt create account with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create account with empty input data.");
        } else if (pojoUser.getUsername() == null || pojoUser.getPassword() == null) {
            logger.error("Attempt create account with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create account with empty input data.");
        } else {
            if(result.hasErrors()) {
                logger.error("Attempt to create account with wrong data structure.");
                return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
            }else{
                return userService.createAccount(pojoUser);
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
