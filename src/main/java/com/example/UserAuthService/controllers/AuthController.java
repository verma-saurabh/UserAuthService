package com.example.UserAuthService.controllers;

import com.example.UserAuthService.entity.User;
import com.example.UserAuthService.requests.LoginRequest;
import com.example.UserAuthService.requests.LogoutRequest;
import com.example.UserAuthService.requests.SignUpRequest;
import com.example.UserAuthService.response.LoginResponse;
import com.example.UserAuthService.response.RefereshTokenResponse;
import com.example.UserAuthService.response.SignUpResponse;
import com.example.UserAuthService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class AuthController {

    @Autowired
    public UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        Optional<String> token = null;
        Optional<User> user;
        try {

            user = userService.findUser(loginRequest.getEmail());
            if (user.isPresent()) {
                token = userService.login(loginRequest);
                loginResponse.setStatus(1);
                loginResponse.setToken(token.get());
                loginResponse.setUser(user.get());
                loginResponse.setMessage("Successfully Logged In");
            } else {
                throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED, "User does not exist");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            loginResponse.setStatus(0);
            loginResponse.setMessage(e.getMessage());
            loginResponse.setUser(null);
            loginResponse.setToken(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        SignUpResponse response = new SignUpResponse();
        Optional<User> user;
        try {
            user = userService.findUser(signUpRequest.getEmail());
            if (user.isPresent()) {
                throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User Already Exists");
            }
            user = userService.signUp(signUpRequest);
            response.setMessage("SignUp Successful");
            response.setStatus(1);
            response.setUser(user.get());

        } catch (Exception e) {
            response.setStatus(0);
            response.setMessage(e.getMessage());
            response.setUser(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/logout")
    public void logout(@RequestBody @Valid LogoutRequest request) {

    }

    @PostMapping(value = "/dummy")
    public ResponseEntity<RefereshTokenResponse> dummy(String userName) {
        String token;
        RefereshTokenResponse response = new RefereshTokenResponse();
        try {
            token = userService.refresh(userName);
            response.setStatus(0);
            response.setMessage("Token Refereshed");
            response.setToken(token);
        } catch (Exception e) {
            response.setStatus(0);
            response.setMessage("Token Expired/Invalid");
            response.setToken(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
