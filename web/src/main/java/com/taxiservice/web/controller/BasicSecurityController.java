package com.taxiservice.web.controller;


import com.taxiservice.model.UserCredentialsService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static com.taxiservice.model.UserCredentialsService.UserData;

public class BasicSecurityController {

    //TODO [herman.zamula]: Implement removing tokens by expires date
    private static final Map<String, UserData<Long>> ENTERED_USERS = newHashMap();
    @Inject
    UserCredentialsService<Long> userCredentials;

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
        UserData<Long> userInfo = userCredentials.checkCredentials(email, password);
        String token = createToken();
        ENTERED_USERS.put(token, userInfo);
        return token;
    }

    protected void remove(String token) {
        checkNotNull(token);
        ENTERED_USERS.remove(token);
    }


    protected long getUserId(String token) {
        return getUser(token).id;
    }

    protected UserData<Long> getUser(String token) {
        return checkNotNull(ENTERED_USERS.get(token), "Application token is not valid");
    }

    public static final Logger LOGGER = Logger.getLogger(BasicSecurityController.class);
}
