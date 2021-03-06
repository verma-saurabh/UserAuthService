package com.example.UserAuthService.service;

import com.example.UserAuthService.entity.User;
import com.example.UserAuthService.repository.UserRepository;
import com.example.UserAuthService.requests.LoginRequest;
import com.example.UserAuthService.requests.LogoutRequest;
import com.example.UserAuthService.requests.SignUpRequest;
import com.example.UserAuthService.response.RefereshTokenResponse;
import com.example.UserAuthService.utils.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public Optional<String> login(LoginRequest request) {
        log.info("Attmpting Sign in");
        Optional<String> token = Optional.empty();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        token = Optional.of(jwtProvider.createToken(request.getEmail()));

        if (token.isPresent()) {
            return token;
        } else {
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed");
        }
    }

    @Override
    public Optional<User> signUp(SignUpRequest request) {
        if (!repository.findByEmail(request.getEmail()).isPresent()) {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            return Optional.of(repository.save(user));
        }
        return Optional.empty();
    }

    @Override
    public void logout(LogoutRequest request) {

    }


    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public String  refresh(String userName) {
        return jwtProvider.createToken(userName);
    }

    @Override
    public Optional<User> findUser(String userName) {
        return repository.findByEmail(userName);
    }
}
