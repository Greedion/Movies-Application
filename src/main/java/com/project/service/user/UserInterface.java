package com.project.service.user;

import com.project.model.FullUser;
import com.project.model.User;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

public interface UserInterface {

     ResponseEntity<List<FullUser>> getAllUsers();

     ResponseEntity<URI> createAccount(User inputUser);
}
