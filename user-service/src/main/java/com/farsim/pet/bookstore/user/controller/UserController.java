package com.farsim.pet.bookstore.user.controller;

import com.farsim.pet.bookstore.user.dto.RegisterRequest;
import com.farsim.pet.bookstore.user.entity.User;
import com.farsim.pet.bookstore.user.service.UserService;
import jakarta.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
    }
}
