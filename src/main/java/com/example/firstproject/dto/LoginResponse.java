package com.example.firstproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Data
//@AllArgsConstructor
//public class LoginResponse {
//    private String token;
//    private String expiredAt;
//}
@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String accessExpiredAt;
}
