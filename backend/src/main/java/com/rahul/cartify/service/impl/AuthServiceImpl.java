package com.rahul.cartify.service.impl;

import org.springframework.stereotype.Service;

import com.rahul.cartify.dto.request.RegisterRequest;
import com.rahul.cartify.dto.response.AuthResponse;
import com.rahul.cartify.entity.User;
import com.rahul.cartify.enums.Provider;
import com.rahul.cartify.enums.Role;
import com.rahul.cartify.repository.UserRepository;
import com.rahul.cartify.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()) != null) {

            return new AuthResponse(
                    null,
                    null,
                    null,
                    "User already exists"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);
        user.setProvider(Provider.LOCAL);

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                "User registered successfully"
        );
    }
}