package com.example.UserAuthService.response;

import com.example.UserAuthService.entity.User;
import lombok.Data;

@Data
public class LoginResponse {
    private int status;
    private String message;
    private User user;
    private String token;
}
