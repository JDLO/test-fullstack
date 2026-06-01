package com.econocom.auth.service;

import com.econocom.auth.domain.dto.LoginRequest;
import com.econocom.auth.domain.dto.LoginResponse;

import java.util.Map;

public interface AuthService {
    LoginResponse authenticateUser(String email, String password);
    String generateSsoRedirectUrl();
    LoginResponse processSsoCallback(String code);
}
