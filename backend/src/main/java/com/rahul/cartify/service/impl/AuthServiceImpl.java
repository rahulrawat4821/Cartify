package com.rahul.cartify.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.cartify.dto.request.RegisterRequest;
import com.rahul.cartify.entity.User;
import com.rahul.cartify.enums.Provider;
import com.rahul.cartify.enums.Role;
import com.rahul.cartify.repository.UserRepository;
import com.rahul.cartify.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String register(RegisterRequest request) {

        // check if user already exists
        if (userRepository.findByEmail(request.getEmail()) != null) {
            return "User already exists";
        }

        // create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // (we will hash later)
        user.setRole(Role.USER);
        user.setProvider(Provider.LOCAL);

        userRepository.save(user);

        return "User registered successfully";
    }
}