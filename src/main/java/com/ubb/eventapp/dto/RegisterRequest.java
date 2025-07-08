package com.ubb.eventapp.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
}
