package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.dto.AuthenticationRequest;
import com.ubb.eventappbackend.dto.AuthenticationResponse;
import com.ubb.eventappbackend.dto.RegisterRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
