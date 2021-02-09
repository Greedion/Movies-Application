package com.project.controller;

import com.project.model.FullUser;
import com.project.model.User;
import com.project.service.user.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

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
    public ResponseEntity<URI> createAccount(@Valid @RequestBody User user) {
        return userService.createAccount(user);
    }
}


