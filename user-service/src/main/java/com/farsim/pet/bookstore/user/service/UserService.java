package com.farsim.pet.bookstore.user.service;

import com.farsim.pet.bookstore.user.dto.RegisterRequest;
import com.farsim.pet.bookstore.user.entity.User;
import com.farsim.pet.bookstore.user.repository.UserRepository;
import com.farsim.pet.bookstore.user.dto.RegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        user.setPassword(request.getPassword()); // Hashes the password before saving
        return userRepository.save(user);
    }
}
