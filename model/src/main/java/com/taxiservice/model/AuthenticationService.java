package com.taxiservice.model;


import com.taxiservice.model.writer.UserManagement;

public interface AuthenticationService {

    UserManagement.UserInfo checkCredentials(String email, String  passwordHash);

    UserManagement.UserInfo readByEmail(String email);

    void createAccessToken(long user, long application);

    void removeAccessToken(long user, long application);

    AccessTokenInfo getAccessToken(long user, long application);


    class AccessTokenInfo {
        public String value;
    }
}
