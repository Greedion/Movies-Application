package com.Movies.Service.User;

import com.Movies.DataTransferObject.UserDTO;
import com.Movies.POJO.POJOUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserInterface {

    public ResponseEntity<List<UserDTO>> getAllUsers();

    public ResponseEntity<?> createAccount(POJOUser inputUser);
}
