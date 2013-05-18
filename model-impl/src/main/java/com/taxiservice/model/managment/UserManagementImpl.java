package com.taxiservice.model.managment;


import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.UserRepository;
import com.taxiservice.model.writer.UserManagement;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;


@Service
public class UserManagementImpl implements UserManagement {


    @Inject
    private UserRepository userRepository;

    public static final Logger LOGGER = Logger.getLogger(UserManagementImpl.class);
    public UserManagementImpl() {
        LOGGER.error("Constructor");
    }


    @Override
    public long createUser(UserInfo userInfo, String passwordHash) {
        checkNotNull(passwordHash);
        return userRepository.save(new User(userInfo.firstName, userInfo.lastName, userInfo.email, passwordHash)).getId();
    }

    @Override
    public void removeUser(long userId) {
        User user = checkNotNull(userRepository.findOne(userId));
        userRepository.delete(user);
    }
}
