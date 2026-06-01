package com.econocom.auth.controller;

import com.econocom.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class SSOController {
    @Autowired
    private JwtUtil jwtUtil;

    private final String MOCK_AUTH_CODE = "sso_decathlon_mock_code_2026";

    @GetMapping("/sso")
    public ResponseEntity<Void> initiateSSO(){
        String frontendCallbackUrl = "http://localhost:4200/sso/callback?code=" + MOCK_AUTH_CODE;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(frontendCallbackUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/sso/callback")
    public ResponseEntity<?> ssoCallback(@RequestParam("code") String code){
        Map<String, Object> response = new HashMap<>();

        if(MOCK_AUTH_CODE.equals(code)){
            String ssoUserEmail = "sso.user@decathlon.com";
            String token = jwtUtil.generateToken(ssoUserEmail);

            response.put("status", "success");
            response.put("message", "Successful SSO authentication through Decathlon");
            response.put("token", token);
            return ResponseEntity.ok(response);
        }else{
            response.put("status", "error");
            response.put("message", "Invalid or expired SSO authorization code.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
