package com.ubb.eventappbackend.security.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService service;

    @BeforeEach
    void setup() {
        service = new JwtService();
        String key = Base64.getEncoder().encodeToString("test-key".getBytes());
        ReflectionTestUtils.setField(service, "secretKey", key);
        ReflectionTestUtils.setField(service, "jwtExpiration", 3600000L);
    }

    @Test
    void generateAndValidateToken() {
        UserDetails user = User.withUsername("user@test.com").password("pass").authorities("USER").build();
        String token = service.generateToken(user);
        assertNotNull(token);
        assertEquals("user@test.com", service.extractUsername(token));
        assertTrue(service.isTokenValid(token, user));
    }
}
