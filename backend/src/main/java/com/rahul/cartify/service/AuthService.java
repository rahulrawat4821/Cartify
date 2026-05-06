package com.rahul.cartify.service;

import com.rahul.cartify.dto.request.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
}