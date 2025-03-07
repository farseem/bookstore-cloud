package com.farsim.pet.bookstore.user.service;

import com.farsim.pet.bookstore.user.dto.request.RegisterRequest;
import com.farsim.pet.bookstore.user.dto.search.UserSearchDTO;
import com.farsim.pet.bookstore.user.entity.User;
import com.farsim.pet.bookstore.user.repository.UserRepository;
import com.farsim.pet.bookstore.user.specifications.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserSpecification userSpecification;

    public UserService(UserRepository userRepository, UserSpecification userSpecification) {
        this.userRepository = userRepository;
        this.userSpecification = userSpecification;
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
                .userType(request.getUserType())
                .build();

        user.setPassword(request.getPassword()); // Hashes the password before saving
        return userRepository.save(user);
    }

    public Page<User> getUsers(UserSearchDTO searchDTO, int page, int size, String sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);

        return userRepository.findAll(userSpecification.buildSpecification(searchDTO), pageable);
    }

}
