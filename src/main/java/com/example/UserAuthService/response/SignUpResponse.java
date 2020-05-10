package com.example.UserAuthService.response;

import com.example.UserAuthService.entity.User;
import lombok.Data;

import java.util.Optional;

@Data
public class SignUpResponse {
    private int status;
    private String message;
    private User user;
}
