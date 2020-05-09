package com.example.UserAuthService.controllers;

import com.example.UserAuthService.entity.User;
import com.example.UserAuthService.requests.LoginRequest;
import com.example.UserAuthService.requests.LogoutRequest;
import com.example.UserAuthService.requests.SignUpRequest;
import com.example.UserAuthService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;

@Slf4j
@RestController(value = "/user/")
public class AuthController {

    @Autowired
    public UserService userService;

    @PostMapping(value = "/login")
    public void login(@RequestBody @Valid LoginRequest loginRequest) {

    }

    @PostMapping(value = "/signup")
    public void signup(@RequestBody @Valid SignUpRequest signUpRequest) {
    }

    @PostMapping(value = "/logout")
    public void logout(@RequestBody @Valid LogoutRequest request) {

    }

    @PostMapping(value = "/dummy")
    public void dummy() {

    }
}
