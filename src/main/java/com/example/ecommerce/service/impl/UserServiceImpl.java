package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.request.RegisterRequest;
import com.example.ecommerce.dto.response.UserResponse;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.EmailAlreadyExistsException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ecommerce.entity.Role;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Initialize the password encoder
    }



    @Override
    public UserResponse registerUser(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return UserResponse.builder().id(savedUser.getId()).name(savedUser.getName()).email(savedUser.getEmail()).role(savedUser.getRole()).build();
    }
}
