package com.econocom.auth.service;

import com.econocom.auth.domain.dto.LoginRequest;
import com.econocom.auth.domain.dto.LoginResponse;
import com.econocom.auth.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void authenticateUser_ShouldReturnSuccessResponse_WhenCredentialsAreValid() {
        // GIVEN
        LoginRequest request = new LoginRequest("admin@admin.com", "password123");
        when(jwtUtil.generateToken("admin@admin.com")).thenReturn("mocked-jwt-token");

        // WHEN
        LoginResponse response = authService.authenticateUser(request.getEmail(), request.getPassword());

        // THEN
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertEquals("Authentication success", response.getMessage());
        assertEquals("mocked-jwt-token", response.getToken());
        verify(jwtUtil, times(1)).generateToken("admin@admin.com");
    }

    @Test
    public void authenticateUser_ShouldReturnErrorResponse_WhenCredentialsAreInvalid() {
        // GIVEN
        LoginRequest request = new LoginRequest("invalid@realdooh.com", "wrongPassword");

        // WHEN
        LoginResponse response = authService.authenticateUser(request.getEmail(), request.getPassword());

        // THEN
        assertNotNull(response);
        assertEquals("error", response.getStatus());
        assertEquals("Credentials are incorrect. Try again", response.getMessage());
        assertNull(response.getToken());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    public void generateSsoRedirectUrl_ShouldBuildUrlWithMockCode() {
        // WHEN
        String url = authService.generateSsoRedirectUrl();

        // THEN
        assertNotNull(url);
        assertTrue(url.contains("code=sso_decathlon_mock_code_2026"));
    }

    @Test
    public void processSsoCallback_ShouldReturnSuccessResponse_WhenCodeIsValid() {
        // GIVEN
        String validCode = "sso_decathlon_mock_code_2026";
        when(jwtUtil.generateToken("sso.user@decathlon.com")).thenReturn("mocked-sso-jwt-token");

        // WHEN
        LoginResponse response = authService.processSsoCallback(validCode);

        // THEN
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertEquals("mocked-sso-jwt-token", response.getToken());
        verify(jwtUtil, times(1)).generateToken("sso.user@decathlon.com");
    }

    @Test
    public void processSsoCallback_ShouldReturnErrorResponse_WhenCodeIsInvalid() {
        // GIVEN
        String invalidCode = "invalid_sso_code_999";

        // WHEN
        LoginResponse response = authService.processSsoCallback(invalidCode);

        // THEN
        assertNotNull(response);
        assertEquals("error", response.getStatus());
        assertNull(response.getToken());
        verify(jwtUtil, never()).generateToken(anyString());
    }
}
