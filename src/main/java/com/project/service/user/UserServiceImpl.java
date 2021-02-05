package com.project.service.user;

import com.project.model.FullUser;
import com.project.entity.UserEntity;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.utils.MapperForUser;
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
    public ResponseEntity<List<FullUser>> getAllUsers() {
        List<UserEntity> allUserEntities = userRepository.findAll();
        List<FullUser> returnObject = new ArrayList<>();
        for (UserEntity x : allUserEntities) {
            returnObject.add(MapperForUser.mapperFromUserEntityToUserDTO(x));
        }
        return ResponseEntity.ok(returnObject);
    }

    @Override
    public ResponseEntity<?> createAccount(User inputUser) {
        if (!userRepository.existsByUsername(inputUser.getUsername())) {
            UserEntity user = new UserEntity(inputUser.getUsername(), passwordEncoder.encode(inputUser.getPassword()), DEFAULT_USER_ROLE);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else{
            logger.error("Received wrong DEFAULT_USER_ROLE or account with this username doesn't exist");
            return ResponseEntity.badRequest().build();}
    }
}