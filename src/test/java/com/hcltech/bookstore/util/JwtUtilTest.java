package com.hcltech.bookstore.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
        "JWT_SECRET_KEY=MzJieXRlc2VjcmV0a2V5Zm9yand0Y2xhaW1nZW5lcmF0aW9uMTIzNA==" // base64 of 32-byte string
})
class JwtUtilTest {
    @InjectMocks
    private JwtUtil jwtUtil;
    @BeforeEach
    void setUp() {
        jwtUtil.SECRET_KEY = "MzJieXRlc2VjcmV0a2V5Zm9yand0Y2xhaW1nZW5lcmF0aW9uMTIzNA=="; // 32-byte key (base64)
    }
    @Test
    void testGenerateTokenAndValidateToken() {
        UserDetails userDetails = new User("john_doe", "password", new ArrayList<>());
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
        boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertTrue(isValid);
    }
    @Test
    void testExtractUsernameFromToken() {
        UserDetails userDetails = new User("john_doe", "password", new ArrayList<>());
        String token = jwtUtil.generateToken(userDetails);
        String username = jwtUtil.extractUsernameFromToken(token);
        assertEquals("john_doe", username);
    }
    @Test
    void testExtractExpirationFromToken() {
        UserDetails userDetails = new User("john_doe", "password", new ArrayList<>());
        String token = jwtUtil.generateToken(userDetails);
        Date expiration = jwtUtil.extractExpirationFromToken(token);
        assertNotNull(expiration);
    }
    @Test
    void testIsTokenExpired_False() {
        UserDetails userDetails = new User("john_doe", "password", new ArrayList<>());
        String token = jwtUtil.generateToken(userDetails);
        boolean isExpired = ReflectionTestUtils.invokeMethod(jwtUtil, "isTokenExpired", token);
        assertFalse(isExpired);
    }
    @Test
    void testExtractAllClaimsFromToken() {
        UserDetails userDetails = new User("john_doe", "password", new ArrayList<>());
        String token = jwtUtil.generateToken(userDetails);
        Claims claims = ReflectionTestUtils.invokeMethod(jwtUtil, "extractAllClaimsFromToken", token);
        assertNotNull(claims);
        assertEquals("john_doe", claims.getSubject());
    }
    @Test
    void testCreateToken_PrivateMethod() {
        Map<String, Object> claims = new HashMap<>();
        String token = ReflectionTestUtils.invokeMethod(jwtUtil, "createToken", claims, "john_doe");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}