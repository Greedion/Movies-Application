package com.project.security.jwtauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
class SecurityConfigurationProperties {
    private String secret_key;
    private int login_token_expiration_time;

    String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(final String secret_key) {
        this.secret_key = secret_key;
    }

    public int getLogin_token_expiration_time() {
        return login_token_expiration_time;
    }

    public void setLogin_token_expiration_time(final int login_token_expiration_time) {
        this.login_token_expiration_time = login_token_expiration_time;
    }
}
