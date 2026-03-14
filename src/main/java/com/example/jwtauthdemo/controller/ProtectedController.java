package com.example.jwtauthdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// Controller demonstrating role-based access using @PreAuthorize
@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    // Endpoint accessible to authenticated users with role USER or ADMIN
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> userAccess() {
        return ResponseEntity.ok("Hello USER/ADMIN — you can access /api/protected/user");
    }

    // Endpoint accessible only to users with role ADMIN
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminAccess() {
        return ResponseEntity.ok("Hello ADMIN — you can access /api/protected/admin");
    }
}
