package com.example.UserAuthService.response;

import lombok.Data;

@Data
public class RefereshTokenResponse {
    private int status;
    private String message;
    private String token;
}
