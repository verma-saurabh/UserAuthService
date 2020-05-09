package com.example.UserAuthService.service;

import com.example.UserAuthService.repository.UserRepository;
import com.example.UserAuthService.requests.LoginRequest;
import com.example.UserAuthService.requests.LogoutRequest;
import com.example.UserAuthService.requests.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public void login(LoginRequest request) {

    }

    @Override
    public void signUp(SignUpRequest request) {

    }

    @Override
    public void logout(LogoutRequest request) {

    }

    @Override
    public void dummy() {

    }
}
