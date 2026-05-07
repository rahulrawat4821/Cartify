package com.rahul.cartify.service;

import com.rahul.cartify.dto.request.RegisterRequest;
import com.rahul.cartify.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
}