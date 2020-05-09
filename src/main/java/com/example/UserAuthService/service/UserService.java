package com.example.UserAuthService.service;

import com.example.UserAuthService.entity.User;
import com.example.UserAuthService.requests.LoginRequest;
import com.example.UserAuthService.requests.LogoutRequest;
import com.example.UserAuthService.requests.SignUpRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public Optional<String> login(LoginRequest request);
    public Optional<User> signUp(SignUpRequest request);
    public void logout(LogoutRequest request);
    public void dummy();
    public List<User> getAllUsers();
}
