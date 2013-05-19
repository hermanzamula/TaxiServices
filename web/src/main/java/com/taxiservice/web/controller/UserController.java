package com.taxiservice.web.controller;

import com.taxiservice.model.writer.UserManagement;
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
    public Long register(@RequestBody UserRequest userInfo) {

        //TODO: Create separate class for user registration data
        return userManagement.createUser(new UserManagement.UserInfo(
                0, userInfo.lastName, userInfo.email, userInfo.name,
                new UserManagement.Place(userInfo.city, userInfo.country)), userInfo.password);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            String token = enter(request.email, request.password);
            return new LoginResponse("You entered successfully", null, token);
        } catch (Exception e) {
            return new LoginResponse(null, e.getMessage(), null);
        }
    }

    private static class UserRequest extends LoginRequest {
        public String name;
        public String lastName;
        public long city;
        public long country;
    }

    private static class LoginRequest {
        public String email;
        public String password;
    }
}
