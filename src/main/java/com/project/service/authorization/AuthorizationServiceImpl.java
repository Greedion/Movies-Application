package com.project.service.authorization;


import com.project.entity.UserEntity;
import com.project.model.User;
import com.project.model.JWTResponse;
import com.project.repository.UserRepository;
import com.project.security.jwtauth.JWTUtils;
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

    private Boolean checkAccountExist(User user) {
        return userRepository.existsByUsername(user.getUsername());
    }

    private UserEntity returnAccount(User user) {
        return userRepository.findByUsername(user.getUsername());
    }

    private Boolean checkCredentials(User user) {
        UserEntity account = returnAccount(user);
        return passwordEncoder.matches(user.getPassword(), account.getPassword());
    }

    @Override
    public ResponseEntity<?> start(User user, AuthenticationManager authenticationManager) {
        if (checkAccountExist(user) && checkCredentials(user)) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
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
        logger.error("Received incorrect logging data.");
        return ResponseEntity.badRequest().build();
    }
}