package com.Movies.Service.User;

import com.Movies.DataTransferObject.UserDTO;
import com.Movies.Entity.UserEntity;
import com.Movies.POJO.POJOUser;
import com.Movies.Repository.UserRepository;
import com.Movies.Utils.MapperForUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserInterface{

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final static String DEFAULT_USER_ROLE = "ROLE_USER";

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserEntity> allUserEntities = userRepository.findAll();
        List<UserDTO> returnObject = new ArrayList<>();
        for (UserEntity x : allUserEntities) {
            returnObject.add(MapperForUser.mapperFromUserEntityToUserDTO(x));
        }
        return ResponseEntity.ok(returnObject);
    }

    @Override
    public ResponseEntity<?> createAccount(POJOUser inputUser) {
        if (!userRepository.existsByUsername(inputUser.getUsername())) {
            UserEntity user = new UserEntity(inputUser.getUsername(), passwordEncoder.encode(inputUser.getPassword()), DEFAULT_USER_ROLE);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else{
            logger.error("Received wrong DEFAULT_USER_ROLE or account with this username doesn't exist");
            return ResponseEntity.badRequest().build();}
    }
}