package com.econocom.auth.controller;


import com.econocom.auth.domain.dto.LoginResponse;
import com.econocom.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SSOControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        // Initializes Mockito annotations for Java 8
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sso_ShouldReturn302Found_AndRedirectToFrontendUrl() throws Exception {
        // GIVEN
        String expectedRedirectUrl = "http://localhost:4200/sso/callback?code=mocked-sso-code";
        when(authService.generateSsoRedirectUrl()).thenReturn(expectedRedirectUrl);

        // WHEN & THEN
        mockMvc.perform(get("/api/auth/sso"))
                .andExpect(status().isFound()) // HTTP 302 Found
                .andExpect(header().string("Location", expectedRedirectUrl));
    }

    @Test
    public void ssoCallback_ShouldReturn200AndToken_WhenSsoCodeIsValid() throws Exception {
        // GIVEN
        LoginResponse mockResponse = new LoginResponse("success", "Autenticación SSO exitosa", "sso-jwt-token-value");
        when(authService.processSsoCallback("valid-sso-code")).thenReturn(mockResponse);

        // WHEN & THEN
        mockMvc.perform(get("/api/auth/sso/callback").param("code", "valid-sso-code"))
                .andExpect(status().isOk()) // HTTP 200 OK
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Autenticación SSO exitosa"))
                .andExpect(jsonPath("$.token").value("sso-jwt-token-value"));
    }

    @Test
    public void ssoCallback_ShouldReturn400BadRequest_WhenSsoCodeIsInvalid() throws Exception {
        // GIVEN
        LoginResponse mockResponse = new LoginResponse("error", "Código inválido", null);
        when(authService.processSsoCallback("invalid-sso-code")).thenReturn(mockResponse);

        // WHEN & THEN
        mockMvc.perform(get("/api/auth/sso/callback").param("code", "invalid-sso-code"))
                .andExpect(status().isBadRequest()) // HTTP 400 Bad Request
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Código inválido"))
                .andExpect(jsonPath("$.token").doesNotExist());
    }
}
