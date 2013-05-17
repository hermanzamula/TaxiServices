package com.taxiservice.model.manager;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserManagement {

    void createUser(UserInfo userInfo, String passwordHash);

    public static class UserInfo {
        public final String firstName;
        public final String lastName;
        public final String email;

        public UserInfo(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
    }
}
