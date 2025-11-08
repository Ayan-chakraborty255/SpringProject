package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String email;
    private String username;
    private String password;

    private Integer signupOtp;

    private boolean accountVerified = false;

    private int signupOtpAttempts = 0;
    private Long signupOtpBlockedUntil = null; 
    private String signupSessionId;
}
