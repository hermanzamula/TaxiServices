package com.taxiservice.model;


import com.taxiservice.model.writer.UserManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserCredentialsService {

    UserManagement.UserInfo checkCredentials(String email, String  passwordHash);

}
