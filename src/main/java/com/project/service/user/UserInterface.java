package com.project.service.user;

import com.project.model.FullUser;
import com.project.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserInterface {

    public ResponseEntity<List<FullUser>> getAllUsers();

    public ResponseEntity<?> createAccount(User inputUser);
}
