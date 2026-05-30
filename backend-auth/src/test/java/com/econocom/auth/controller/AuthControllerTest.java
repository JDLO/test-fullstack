package com.econocom.auth.controller;

import com.econocom.auth.domain.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void login_ShouldReturnToken_WhenCredentialsAreCorrect() throws Exception{
        LoginRequest request = new LoginRequest("admin@admin.com","somepassword123");

        mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.message").value("Authentication success"))
                    .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void login_ShouldReturnToken_WhenCredentialsAreIncorrect() throws Exception{
        LoginRequest request = new LoginRequest("admin@fake.com","password123Fail");

        mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value("error"))
                    .andExpect(jsonPath("$.message").value("Credentials are incorrect. Try again"))
                    .andExpect(jsonPath("$.token").doesNotExist());
    }

    @Test
    public void sso_ShouldRedirectWithState302() throws Exception {
        mockMvc.perform(post("/api/auth/sso")) // Nota: El documento pide GET/petición, lo manejamos por GET
                .andReturn();

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/auth/sso"))
                .andExpect(status().isFound()) // Verifica el HTTP 302
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.header().string("Location",
                        "http://localhost:4200/sso/callback?code=sso_decathlon_mock_code_2026"));
    }

    @Test
    public void ssoCallback_ShouldReturnToken_WhenCodeIsValid() throws Exception {
        // WHEN & THEN: Enviamos el código correcto al callback y esperamos el JWT
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/auth/sso/callback")
                        .param("code", "sso_decathlon_mock_code_2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void ssoCallback_ShouldReturn400_WhenCodeIsInvalid() throws Exception {
        // WHEN & THEN: Enviamos un código erróneo y esperamos un Bad Request
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/auth/sso/callback")
                        .param("code", "fake_code_999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.token").doesNotExist());
    }
}
