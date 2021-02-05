package com.project.security;

import com.project.entity.UserEntity;
import com.project.repository.UserRepository;
import com.project.security.jwtauth.AuthEntryPointJwt;
import com.project.security.jwtauth.JWTFilter;
import com.project.security.jwtauth.JWTUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JWTUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserRepository userRepository,
                             AuthEntryPointJwt unauthorizedHandler,
                             JWTUtils jwtUtils,
                             UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    JWTFilter authenticationJwtTokenFilter() {
        return new JWTFilter(jwtUtils, userDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .cors()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "api/user/createaccount").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            http.csrf().disable();

    }

    @EventListener(ApplicationReadyEvent.class)
    public void createSampleUser() {
        UserEntity user = new UserEntity("User", passwordEncoder().encode("User"), "ROLE_USER");
        UserEntity admin = new UserEntity("Admin", passwordEncoder().encode("Admin"), "ROLE_ADMIN");
        userRepository.save(user);
        userRepository.save(admin);
    }
}