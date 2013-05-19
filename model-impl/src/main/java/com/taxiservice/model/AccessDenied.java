package com.taxiservice.model;


public class AccessDenied extends RuntimeException {
    public AccessDenied(String s) {
        super(s);
    }
}
