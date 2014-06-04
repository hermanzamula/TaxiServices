package com.taxiservice.web.controller;


import com.taxiservice.model.AuthenticationService;
import com.taxiservice.model.writer.UserManagement;
import com.taxiservice.web.security.RichUser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

public class BasicSecurityController {

    //TODO [herman.zamula]: Implement removing tokens by expires date
    private static final Map<String, UserDetails> ENTERED_USERS = newHashMap();
    @Inject
    AuthenticationService userCredentials;

    private SecureRandom secureRandom = new SecureRandom();

    private String createToken() {
        return new BigInteger(512, secureRandom).toString(32);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String errorHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(500, ex.getMessage());
        LOGGER.log(Level.ERROR, ex.getMessage());
        return "Uncaught Error: " + ex.getMessage();
    }

    protected String enter(String email, String password) {
        UserManagement.UserInfo userInfo = userCredentials.checkCredentials(email, password);
        String token = createToken();
        ENTERED_USERS.put(token, newUserDetails(userInfo));
        return token;
    }

    protected void remove(String token) {
        checkNotNull(token);
        ENTERED_USERS.remove(token);
    }

    private UserDetails newUserDetails(UserManagement.UserInfo userInfo) {
        return new UserDetails(userInfo.id, userInfo.firstName, userInfo.lastName, userInfo.place.city);
    }

    protected long getUserId(String token) {
        return getUser(token).id;
    }

    protected long getUserId(Principal principal) {
        final RichUser details = (RichUser) ((Authentication) principal).getDetails();
        return details.getId();
    }

    protected UserDetails getUser(String token) {
        return checkNotNull(ENTERED_USERS.get(token), "Application token is not valid");
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

    public static final Logger LOGGER = Logger.getLogger(BasicSecurityController.class);
}
