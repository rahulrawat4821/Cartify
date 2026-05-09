package com.rahul.cartify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.rahul.cartify.dto.request.LoginRequest;
import com.rahul.cartify.dto.request.RegisterRequest;
import com.rahul.cartify.dto.response.LoginResponse;
import com.rahul.cartify.dto.response.RegisterResponse;
import com.rahul.cartify.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @GetMapping("/secure")
public String secure() {
    return "Protected route working";
}
}