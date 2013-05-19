package com.taxiservice.web.controller;


import com.google.common.collect.ImmutableMap;
import com.taxiservice.model.UserCredentialsService;
import com.taxiservice.model.writer.UserManagement;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;

public class BasicSecurityController {

    private static final Map<String, UserDetails> ENTERED_USERS = ImmutableMap.of();
    @Inject
    UserCredentialsService userCredentials;

    private static String createToken() {
        return String.valueOf(new Random().nextLong());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String errorHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return "Uncaught Error: " + ex.getMessage();
    }

    protected String enter(String email, String password) {
        UserManagement.UserInfo userInfo = userCredentials.checkCredentials(email, password);
        String token = createToken();
        ENTERED_USERS.put(token, newUserDetails(userInfo));
        return token;
    }

    private UserDetails newUserDetails(UserManagement.UserInfo userInfo) {
        return new UserDetails(userInfo.id, userInfo.firstName, userInfo.lastName, userInfo.place.city);
    }

    protected long getUserId(String token) {
        return getUser(token).id;
    }

    protected UserDetails getUser(String token) {
        return ENTERED_USERS.get(token);
    }

    public static class UserDetails {
        public final long id;
        public final String firstName;
        public final String lastName;
        public final long city;


        public UserDetails(long id, String firstName, String lastName, long city) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.city = city;
        }
    }
}
