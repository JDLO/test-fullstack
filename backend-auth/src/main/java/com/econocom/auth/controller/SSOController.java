package com.econocom.auth.controller;

import com.econocom.auth.domain.dto.LoginResponse;
import com.econocom.auth.security.JwtUtil;
import com.econocom.auth.service.AuthService;
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
    private AuthService authService;

    @GetMapping("/sso")
    public ResponseEntity<Void> initiateSSO(){
        String frontendCallbackUrl = this.authService.generateSsoRedirectUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(frontendCallbackUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/sso/callback")
    public ResponseEntity<LoginResponse> ssoCallback(@RequestParam("code") String code){
        LoginResponse response = new LoginResponse();
        response = this.authService.processSsoCallback(code);

        if(response.getStatus().equals("success")){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
