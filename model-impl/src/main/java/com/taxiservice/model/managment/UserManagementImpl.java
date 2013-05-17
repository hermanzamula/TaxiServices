package com.taxiservice.model.managment;


import com.taxiservice.model.entity.User;
import com.taxiservice.model.manager.UserManagement;
import com.taxiservice.model.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;


@Service
public class UserManagementImpl implements UserManagement {


    private final UserRepository userRepository;


    @Inject
    public UserManagementImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void createUser(UserInfo userInfo, String passwordHash) {
       checkNotNull(passwordHash);
       userRepository.save(new User(userInfo.firstName, userInfo.lastName, userInfo.email, passwordHash));
    }
}
