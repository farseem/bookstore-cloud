package com.farsim.pet.bookstore.us.controller;

import com.farsim.pet.bookstore.us.security.JwtService;
import com.farsim.pet.bookstore.us.security.RedisTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final RedisTokenService redisTokenService;

    public AuthController(JwtService jwtService, RedisTokenService redisTokenService) {
        this.jwtService = jwtService;
        this.redisTokenService = redisTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username) {
        // Generate JWT
        String token = jwtService.generateToken(username);

        // Store token in Redis (Optional)
        redisTokenService.storeToken(username, token);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String username) {
        // Remove JWT from Redis
        redisTokenService.deleteToken(username);
        return ResponseEntity.ok("Logged out successfully");
    }
}
