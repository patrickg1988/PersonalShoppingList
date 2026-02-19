package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public void register(String username, String rawPassword) {
    if (userRepo.existsByUsername(username)) {
        throw new IllegalArgumentException("Benutzername ist bereits vergeben.");
    }

    String hash = encoder.encode(rawPassword);

    //.builder, weil Lombok den Konstruktor String, String, String nicht bereitstellt
    AppUser user = AppUser.builder()
        .username(username.trim())
        .passwordHash(hash)
        .role("USER")
        .build();

    userRepo.save(user);
}

}

