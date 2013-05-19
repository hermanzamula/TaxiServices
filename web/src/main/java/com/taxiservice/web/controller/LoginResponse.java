package com.taxiservice.web.controller;


public class LoginResponse {

    public final String successMessage;
    public final String errorMessage;
    public final String token;

    public LoginResponse(String successMessage, String errorMessage, String token) {
        this.successMessage = successMessage;
        this.errorMessage = errorMessage;
        this.token = token;
    }
}
