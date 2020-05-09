package com.example.UserAuthService.controllers;

import com.example.UserAuthService.entity.User;
import com.example.UserAuthService.requests.LoginRequest;
import com.example.UserAuthService.requests.LogoutRequest;
import com.example.UserAuthService.requests.SignUpRequest;
import com.example.UserAuthService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class AuthController {

    @Autowired
    public UserService userService;

    @PostMapping(value = "/login")
    public String login(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.login(loginRequest).
                orElseThrow(() ->
                        new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));
    }

    @PostMapping(value = "/signup")
    public User signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User Already Exists"));
    }

    @PostMapping(value = "/logout")
    public void logout(@RequestBody @Valid LogoutRequest request) {

    }

    @PostMapping(value = "/dummy")
    public void dummy() {

    }

    @GetMapping(value = "/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
