package com.example.FinanceTracker.service;

import com.example.FinanceTracker.dto.request.LoginRequest;
import com.example.FinanceTracker.dto.request.RegiesterRequest;
import com.example.FinanceTracker.entity.User;
import com.example.FinanceTracker.exception.DuplicateResourceException;
import com.example.FinanceTracker.exception.ForbiddenException;
import com.example.FinanceTracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String registerRequest(RegiesterRequest request) {
        //check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("E-mail already exists");
        }

        //check username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        //create entity
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        //hash password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        userRepository.save(user);
        return "Successfully Registered";
    }

    public String loginRequest(LoginRequest request) {
        Optional<User> availableUser = userRepository.findByEmail(request.getEmail());

        if (availableUser.isEmpty()) {
            throw new ForbiddenException("Invalid User");
        }
        User user = availableUser.get();
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(request.getEmail());
            return token;
        } else {
            throw new ForbiddenException("Invalid password");
        }
    }
}
