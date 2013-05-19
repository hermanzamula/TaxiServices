package com.taxiservice.model.test;


import com.taxiservice.model.writer.UserManagement;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Random;

public class UserTest{

    @Inject
    protected UserManagement userManagement;

    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }



    @Test
    public void testSaveUser() {
        userManagement.createUser(new UserManagement.UserInfo(0, "Zamula", "h@hh.com", "Herman", null), anyValue());
    }

    private String anyValue() {
        return new Random().toString();
    }

}