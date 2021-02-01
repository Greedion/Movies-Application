package com.movies.Security;
import com.movies.Entity.UserEntity;
import com.movies.Repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final
    private UserDetailsServiceImpl userDetailsService;
    final
    private UserRepository userRepository;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
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
                .antMatchers("/api/movie/update").hasRole("ADMIN")
                .antMatchers("/api/movie/add").hasRole("ADMIN")
                .antMatchers("/api/review/addReviewForMovie").hasRole("USER")
                .antMatchers("/api/review/deleteReview").hasRole("USER")
                .antMatchers("/api/review/like").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/movie/likeMovie").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/movie/addRating").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/review/getAllByMovie").permitAll()
                .antMatchers("/api/movie/getAll").permitAll()
                .antMatchers("/api/movie/getDetails").permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createSampleUser() {
        UserEntity user = new UserEntity("User", passwordEncoder().encode("User"), "ROLE_USER");
        UserEntity admin = new UserEntity("Admin", passwordEncoder().encode("Admin"), "ROLE_ADMIN");
        userRepository.save(user);
        userRepository.save(admin);
    }
}
