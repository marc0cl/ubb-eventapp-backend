package com.ubb.eventapp.service;

import com.ubb.eventapp.dto.AuthenticationRequest;
import com.ubb.eventapp.dto.AuthenticationResponse;
import com.ubb.eventapp.dto.RegisterRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
