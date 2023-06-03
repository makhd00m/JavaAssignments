package com.scaler.blogapi.security;

import com.scaler.blogapi.security.jwt.JWTTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTTokenServiceTests {
    private final JWTTokenService jwtTokenService = new JWTTokenService();

    @Test
    public void testCreateAuthToken() {
        String username = "User001";
        String token = jwtTokenService.createAuthToken(username);
        System.out.println(token);
        Assertions.assertNotNull(token);
    }

    @Test
    public void testTokenVerification() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyMDAxIiwiaXNzIjoiYmxvZy1hcGkiLCJpYXQiOjE2ODUzNjg2NTYsImV4cCI6MTY4NTQ1NTA1Nn0.NlIPbUPmX4-9WSLfL4K_WNgNo7UrRvcdWikHrbneVVM";
        String username = jwtTokenService.getUsernameFromToken(token);
        Assertions.assertEquals("User001", username);
    }
}
