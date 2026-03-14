package com.example.jwtauthdemo.config;

import com.example.jwtauthdemo.model.User;
import com.example.jwtauthdemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

// This initializer creates two users on startup:
// 1) username: user / password: password (role USER)
// 2) username: admin / password: adminpass (role ADMIN)
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("user")) {
                User u = new User("user", passwordEncoder.encode("password"), "USER");
                userRepository.save(u);
            }
            if (!userRepository.existsByUsername("admin")) {
                User a = new User("admin", passwordEncoder.encode("adminpass"), "ADMIN");
                userRepository.save(a);
            }
        };
    }
}
