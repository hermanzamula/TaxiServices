package com.taxiservice.model.test;


import com.taxiservice.model.entity.User;
import com.taxiservice.model.managment.UserManagementImpl;
import com.taxiservice.model.repository.UserRepository;
import com.taxiservice.model.writer.UserManagement;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

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
        userManagement.createUser(new UserManagement.UserInfo("Herman", "Zamula", "h@hh.com"), anyValue());
    }

    private String anyValue() {
        return new Random().toString();
    }

}