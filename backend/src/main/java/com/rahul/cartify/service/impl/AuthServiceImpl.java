package com.rahul.cartify.service.impl;

import org.springframework.stereotype.Service;

import com.rahul.cartify.dto.request.RegisterRequest;
import com.rahul.cartify.dto.response.RegisterResponse;
import com.rahul.cartify.dto.request.LoginRequest;
import com.rahul.cartify.dto.response.LoginResponse;
import com.rahul.cartify.entity.User;
import com.rahul.cartify.enums.Provider;
import com.rahul.cartify.enums.Role;
import com.rahul.cartify.repository.UserRepository;
import com.rahul.cartify.security.JwtService;
import com.rahul.cartify.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthServiceImpl(UserRepository userRepository , PasswordEncoder passwordEncoder, JwtService jwtService) {
          this.userRepository = userRepository;
          this.passwordEncoder = passwordEncoder;
          this.jwtService = jwtService;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()) != null) {

            return new RegisterResponse(
                    null,
                    null,
                    null,
                    "User already exists",
                    null
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setProvider(Provider.LOCAL);

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                "User registered successfully",
                savedUser.getRole()
        );
    }


      @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(
                request.getEmail()
        );

        if (user == null) {
            throw new RuntimeException(
                    "User not found"
            );
        }

        boolean isPasswordMatch =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!isPasswordMatch) {
            throw new RuntimeException(
                    "Invalid password"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new LoginResponse(token);
    }

}