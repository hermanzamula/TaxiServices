package com.taxiservice.model.writer;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserManagement {

    long createUser(UserInfo userInfo, String passwordHash);

    void removeUser(long userId);

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
