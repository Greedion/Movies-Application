package com.project.security.jwtauth;

import com.project.entity.UserEntity;
import com.project.exception.ExceptionsMessageArchive;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);
    private final SecurityConfigurationProperties securityConfigurationProperties;

    JWTUtils(final SecurityConfigurationProperties securityConfigurationProperties) {
        this.securityConfigurationProperties = securityConfigurationProperties;
    }

    public String generateJwtToken(Authentication authentication) {
        UserEntity userPrincipal = (UserEntity) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() +
                        securityConfigurationProperties.getLogin_token_expiration_time()))
                .signWith(SignatureAlgorithm.HS512, securityConfigurationProperties.getSecret_key())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(securityConfigurationProperties.getSecret_key()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(securityConfigurationProperties.getSecret_key()).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error(ExceptionsMessageArchive.JWT_U_INVALID_JWT_TOKEN_LOG, e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error(ExceptionsMessageArchive.JWT_U_JWT_TOKEN__EXPIRED_LOG, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error(ExceptionsMessageArchive.JWT_U_JWT_TOKEN_IS_UNSUPPORTED, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(ExceptionsMessageArchive.JWT_U_JWT_TOKEN_CLAIMS_STRING_IS_EMPTY, e.getMessage());
        }
        return false;
    }
}