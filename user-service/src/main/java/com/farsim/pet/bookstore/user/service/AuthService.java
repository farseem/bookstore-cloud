package com.farsim.pet.bookstore.user.service;

import com.farsim.pet.bookstore.user.entity.User;
import com.farsim.pet.bookstore.user.enums.Role;
import com.farsim.pet.bookstore.user.repository.UserRepository;
import com.farsim.pet.bookstore.user.dto.AuthRequest;
import com.farsim.pet.bookstore.user.dto.AuthResponse;
import com.farsim.pet.bookstore.user.dto.RegisterRequest;
import com.farsim.pet.bookstore.user.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .userType(request.getUserType())
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
