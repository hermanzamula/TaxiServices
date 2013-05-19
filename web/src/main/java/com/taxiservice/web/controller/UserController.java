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
public class UserController {

    private final UserManagement userManagement;

    @Inject
    public UserController(UserManagement userManagement) {
        this.userManagement = userManagement;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Long addUser(@RequestBody UserRequest userInfo){
        return userManagement.createUser(new UserManagement.UserInfo(
                userInfo.name,
                userInfo.lastName,
                userInfo.email), userInfo.password);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getUsers(){
        return "Ololo";
    }

    private static class UserRequest {
        public String name;
        public String lastName;
        public String email;
        public String password;
    }
}
