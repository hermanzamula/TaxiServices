package com.taxiservice.model.writer;


import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Transactional
public interface UserManagement<ID> {

    long createUser(UserInfo userData, String password);

    void removeUser(@NotNull ID userId);

    void updateUserInfo(@NotNull ID userId, UserInfo userInfo);

    void updatePassword(@NotNull ID user, String oldPassword, String password);

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
