package com.example.UserAuthService.service;

import com.example.UserAuthService.requests.LoginRequest;
import com.example.UserAuthService.requests.LogoutRequest;
import com.example.UserAuthService.requests.SignUpRequest;

public interface UserService {

    public void login(LoginRequest request);
    public void signUp(SignUpRequest request);
    public void logout(LogoutRequest request);
    public void dummy();
}
