package com.Movies.Service.Authorization;


import com.Movies.Entity.UserEntity;
import com.Movies.POJO.POJOUser;
import com.Movies.Payload.JWTResponse;
import com.Movies.Repository.UserRepository;
import com.Movies.Security.JWTAuth.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorizationServiceImpl implements AuthorizationInterface {

    private final Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    public AuthorizationServiceImpl(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder,
                                    JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    private Boolean checkAccountExist(POJOUser pojoUser) {
        return userRepository.existsByUsername(pojoUser.getUsername());
    }

    private UserEntity returnAccount(POJOUser pojoUser) {
        return userRepository.findByUsername(pojoUser.getUsername());
    }

    private Boolean checkCredentials(POJOUser pojoUser) {
        UserEntity account = returnAccount(pojoUser);
        return passwordEncoder.matches(pojoUser.getPassword(), account.getPassword());
    }

    @Override
    public ResponseEntity<?> start(POJOUser pojoUser, AuthenticationManager authenticationManager) {
        if (checkAccountExist(pojoUser)) {
            if (checkCredentials(pojoUser)) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(pojoUser.getUsername(), pojoUser.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);
                UserEntity userDetails = (UserEntity) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(new JWTResponse(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        roles));
            }
        }
        logger.error("Received incorrect logging data.");
        return ResponseEntity.badRequest().build();
    }
}