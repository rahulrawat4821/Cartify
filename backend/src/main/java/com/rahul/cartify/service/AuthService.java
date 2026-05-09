package com.rahul.cartify.service;

import com.rahul.cartify.dto.request.LoginRequest;
import com.rahul.cartify.dto.request.RegisterRequest;
import com.rahul.cartify.dto.response.LoginResponse;
import com.rahul.cartify.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);  

}