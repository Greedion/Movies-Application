package com.movies.firstversion.Seciurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSeciurityConfig extends WebSecurityConfigurerAdapter {

    UserDetailsServiceImpl userDetailsService;
    UserRepository userRepository;

    @Autowired
    public WebSeciurityConfig(UserDetailsServiceImpl userDetailsService, UserRepository userRepository){
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/movie/update").hasRole("ADMIN")
                .antMatchers("/movie/add").hasRole("ADMIN")
                .antMatchers("/review/addReviewForMovie").hasRole("USER")
                .antMatchers("/review/deleteReview").hasRole("USER")
                .antMatchers("/review/like").hasAnyRole("ADMIN", "USER")
                .antMatchers("/movie/likeMovie").hasAnyRole("ADMIN", "USER")
                .antMatchers("/movie/addRating").hasAnyRole("ADMIN", "USER")
                .antMatchers("review/getAllByMovie").permitAll()
                .antMatchers("movie/getAll").permitAll()
                .antMatchers("movie/getDetails").permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @EventListener(ApplicationReadyEvent.class)
    public void createSampleUser(){
        UserEntity user = new UserEntity("User", passwordEncoder().encode("User"), "ROLE_USER");
        UserEntity admin = new UserEntity("Admin", passwordEncoder().encode("Admin"), "ROLE_ADMIN");
        userRepository.save(user);
        userRepository.save(admin);
    }

}
