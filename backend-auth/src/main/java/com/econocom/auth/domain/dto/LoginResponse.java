package com.econocom.auth.domain.dto;

public class LoginResponse {
    private String message;
    private String status;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String status,String message, String token) {
        super();
        this.message = message;
        this.token = token;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
