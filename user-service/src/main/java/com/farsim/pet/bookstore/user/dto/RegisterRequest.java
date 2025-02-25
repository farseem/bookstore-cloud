package com.farsim.pet.bookstore.user.dto;

import com.farsim.pet.bookstore.user.enums.Role;
import com.farsim.pet.bookstore.user.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private Role role; // Admin, Customer, Author
    private UserType userType; // Student, Business, Individual
}
