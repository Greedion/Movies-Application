package com.Movies.Security;
import com.Movies.Entity.UserEntity;
import com.Movies.Repository.UserRepository;
import com.Movies.Security.JWTAuth.AuthEntryPointJwt;
import com.Movies.Security.JWTAuth.JWTFilter;
import com.Movies.Security.JWTAuth.JWTUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    JWTFilter authenticationJwtTokenFilter(){
        return new JWTFilter(jwtUtils, userDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "api/user/createaccount").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.cors().disable();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createSampleUser() {
            UserEntity user = new UserEntity("User", passwordEncoder().encode("User"), "ROLE_USER");
            UserEntity admin = new UserEntity("Admin", passwordEncoder().encode("Admin"), "ROLE_ADMIN");
            userRepository.save(user);
            userRepository.save(admin);
    }
}