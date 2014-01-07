package com.taxiservice.web.controller;

import com.taxiservice.model.writer.UserManagement;
import com.taxiservice.web.request.LoginRequest;
import com.taxiservice.web.request.UserCreationRequest;
import com.taxiservice.web.response.LoginResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@RequestMapping("/user")
public class UserController extends BasicSecurityController {

    private final UserManagement userManagement;

    @Inject
    public UserController(UserManagement userManagement) {
        this.userManagement = userManagement;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public LoginResponse register(@RequestBody UserCreationRequest request) {
        userManagement.createUser(new UserManagement.UserInfo(
                request.firstName, request.lastName, request.email,
                new UserManagement.Place(request.city, request.country)),
                request.password
        );
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = request.email;
        loginRequest.password = request.password;
        return login(loginRequest);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            String token = enter(request.email, request.password);
            return new LoginResponse("You entered successfully", null, token);
        } catch (RuntimeException e) {
            return new LoginResponse(null, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public void remove(@RequestBody String token) {
        super.remove(token);
    }

}
