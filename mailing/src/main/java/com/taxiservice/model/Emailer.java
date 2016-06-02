package com.taxiservice.model;

import org.springframework.scheduling.annotation.Async;

@Async
public interface Emailer {
    void send(String to, String title, String message);
}
