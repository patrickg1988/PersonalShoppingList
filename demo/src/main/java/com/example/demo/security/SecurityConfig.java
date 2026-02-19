package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.repository.UserRepository;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepo;

    public SecurityConfig(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByUsername(username)
            .map(u -> org.springframework.security.core.userdetails.User.builder()
                .username(u.getUsername())
                .password(u.getPasswordHash())
                // WICHTIG: roles(...) erwartet "USER" und macht daraus ROLE_USER
                // Wenn du in DB "USER" speicherst -> ok.
                .roles(u.getRole())
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers(h -> h.frameOptions(f -> f.disable()));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/welcome", "/register", "/login", "/css/**", "/js/**", "/images/**", "/h2-console/**", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .build();
    }
}
