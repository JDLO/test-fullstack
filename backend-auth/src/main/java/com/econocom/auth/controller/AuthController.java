package com.econocom.auth.controller;

import com.econocom.auth.domain.dto.LoginRequest;
import com.econocom.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        String mockEmail = "";
        String mockPassword = "";

        if(mockEmail.equalsIgnoreCase(loginRequest.getEmail()) &&
            mockPassword.equals(loginRequest.getPassword())){

            String token = jwtUtil.generateToken(loginRequest.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Autenticación exitosa");
            response.put("token", token);

            return ResponseEntity.ok(response);
        }else{
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Credentials are incorrect. Try again");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
