package com.example.FinanceTracker.controller;

import com.example.FinanceTracker.dto.request.LoginRequest;
import com.example.FinanceTracker.dto.request.RegiesterRequest;
import com.example.FinanceTracker.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication APIs")
public class AuthController {

    private final AuthService authservice;

    @PostMapping("/register")
    public String registerRequest(@RequestBody RegiesterRequest request) {
        return authservice.registerRequest(request);
    }

    @PostMapping("/login")
    public String loginRequest(@RequestBody LoginRequest request) {
        return authservice.loginRequest(request);
    }

}
