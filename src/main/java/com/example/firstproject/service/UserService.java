package com.example.firstproject.service;

import com.example.firstproject.dto.LoginRequest;
import com.example.firstproject.dto.LoginResponse;
import com.example.firstproject.dto.RegisterRequest;

public interface UserService {
    void register(RegisterRequest req);
    LoginResponse login(LoginRequest req);
}
