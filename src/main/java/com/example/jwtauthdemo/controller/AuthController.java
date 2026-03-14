package com.example.jwtauthdemo.controller;

import com.example.jwtauthdemo.controller.dto.LoginRequest;
import com.example.jwtauthdemo.controller.dto.SignupRequest;
import com.example.jwtauthdemo.controller.dto.JwtResponse;
import com.example.jwtauthdemo.model.User;
import com.example.jwtauthdemo.repository.UserRepository;
import com.example.jwtauthdemo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // Register endpoint - create a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        String role = signupRequest.getRole() == null ? "USER" : signupRequest.getRole().toUpperCase();

        User user = new User(signupRequest.getUsername(), encodedPassword, role);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // Login endpoint - authenticate and return JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
