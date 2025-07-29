package com.ubb.eventapp.security.services;

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
        String keyMaterial = "0123456789abcdef0123456789abcdef"; // 32 bytes
        String key = Base64.getEncoder().encodeToString(keyMaterial.getBytes());
        ReflectionTestUtils.setField(service, "secretKey", key);
        ReflectionTestUtils.setField(service, "jwtExpiration", 3600000L);
    }

    @Test
    void generateAndValidateToken() {
        UserDetails user = User.withUsername("user@test.com").password("pass").authorities("USER").build();
        String token = service.generateToken(java.util.Map.of("userId", 1L), user);
        assertNotNull(token);
        assertEquals("user@test.com", service.extractUsername(token));
        assertEquals(1L, (Long) service.extractClaim(token, c -> c.get("userId", Long.class)));
        assertTrue(service.isTokenValid(token, user));
    }
}
