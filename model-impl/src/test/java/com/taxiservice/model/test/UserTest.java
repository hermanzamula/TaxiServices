package com.taxiservice.model.test;


import com.taxiservice.model.writer.UserManagement;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Random;

public class UserTest{

    @Inject
    private   UserManagement userManagement;

    public UserTest() {
    }

    @Test
    public void testSaveUser() {
        userManagement.createUser(new UserManagement.UserInfo(0, "Zamula", "h@hh.com", "Herman", null), anyValue());
    }

    private String anyValue() {
        return new Random().toString();
    }

}