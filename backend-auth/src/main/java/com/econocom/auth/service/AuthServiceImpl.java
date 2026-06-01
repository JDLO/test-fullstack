package com.econocom.auth.service;

import com.econocom.auth.domain.dto.LoginRequest;
import com.econocom.auth.domain.dto.LoginResponse;
import com.econocom.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    private final String MOCK_EMAIL = "admin@admin.com";
    private final String MOCK_PASSWORD = "password123";
    private final String MOCK_AUTH_CODE = "sso_decathlon_mock_code_2026";

    @Override
    public LoginResponse authenticateUser(String email, String password) {

        if(MOCK_EMAIL.equalsIgnoreCase(email) && MOCK_PASSWORD.equals(password)){

            String token = jwtUtil.generateToken(email);

            LoginResponse response = new LoginResponse(
                    "success",
                    "Authentication success",
                    token
            );

            return response;
        }else{
            LoginResponse errorResponse = new LoginResponse();
            errorResponse.setStatus("error");
            errorResponse.setMessage( "Credentials are incorrect. Try again");

            return errorResponse;
        }
    }

    @Override
    public String generateSsoRedirectUrl() {
        return "http://localhost:4200/sso/callback?code=" + MOCK_AUTH_CODE;
    }

    @Override
    public LoginResponse processSsoCallback(String code) {
        LoginResponse result = new LoginResponse();

        if (MOCK_AUTH_CODE.equals(code)) {
            String ssoUserEmail = "sso.user@decathlon.com";
            String token = jwtUtil.generateToken(ssoUserEmail);
            result.setStatus("success");
            result.setToken(token);
            result.setMessage( "Successful SSO authentication through Decathlon");
        } else {
            result.setStatus("error");
            result.setMessage( "Invalid or expired SSO authorization code.");
        }
        return result;
    }
}
