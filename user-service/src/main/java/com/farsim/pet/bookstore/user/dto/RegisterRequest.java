package com.farsim.pet.bookstore.user.dto;

import com.farsim.pet.bookstore.user.enums.Role;
import com.farsim.pet.bookstore.user.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
    private UserType userType;
}
