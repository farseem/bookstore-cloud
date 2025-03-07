package com.farsim.pet.bookstore.user.controller;

import com.farsim.pet.bookstore.user.dto.PaginationDTO;
import com.farsim.pet.bookstore.user.dto.request.RegisterRequest;
import com.farsim.pet.bookstore.user.dto.search.UserSearchDTO;
import com.farsim.pet.bookstore.user.entity.User;
import com.farsim.pet.bookstore.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
            @ModelAttribute UserSearchDTO searchDTO,
            @ModelAttribute PaginationDTO paginationDTO) {

        Page<User> users = userService.getUsers(
                searchDTO,
                paginationDTO.getPage(),
                paginationDTO.getSize(),
                paginationDTO.getSortBy(),
                paginationDTO.getSortDirection()
        );

        return ResponseEntity.ok(users);
    }
    /*
    //Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //Update User
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    //Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }*/
}
