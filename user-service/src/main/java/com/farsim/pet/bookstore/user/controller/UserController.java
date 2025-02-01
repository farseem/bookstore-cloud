package com.farsim.pet.bookstore.us.controller;

import com.farsim.pet.bookstore.us.dto.UserRegistrationRequest;
import com.farsim.pet.bookstore.us.entity.User;
import com.farsim.pet.bookstore.us.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
    }
}
