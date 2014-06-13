package com.taxiservice.model.writer;


import com.taxiservice.model.AccessDenied;
import com.taxiservice.model.Validator;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;


@Service
public class UserManagementImpl implements UserManagement<Long> {

    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder encoder;

    @Inject
    public UserManagementImpl(UserRepository userRepository,
                              Validator validator, PasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.validator = validator;
        this.encoder = encoder;
    }

    @Override
    public long createUser(UserInfo userData, String password) {
        checkNotNull(password);
        if (validator.isUserCanBeCreated(userData.email)) {
            throw new AccessDenied("Can't create user with email '" + userData.email + "'");
        }
        final String passwordHash = encoder.encode(password);
        User user = new User(userData.firstName, userData.lastName, userData.email, passwordHash);
        return updateUser(userData, user);
    }

    private long updateUser(UserInfo userInfo, User user) {
        final Long id = saveUser(user);
//        saveUserPlace(user, city);
        return id;
    }

    private Long saveUser(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public void updateUserInfo(Long userId, UserInfo userInfo) {
        User user = checkNotNull(userRepository.findOne(userId));
        user.setFirstName(userInfo.firstName);
        user.setLastName(userInfo.lastName);
        updateUser(userInfo, user);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String password) {
        User user = checkNotNull(userRepository.findOne(userId));

        //TODO: implement password hash encoding
        if (!user.getPasswordHash().equals(encoder.encode(oldPassword))) {
            throw new AccessDenied("Old password is invalid");
        }

        final String passwordHash = encoder.encode(password);
        user.setPasswordHash(passwordHash);
    }

    @Override
    public void removeUser(Long userId) {
        User user = checkNotNull(userRepository.findOne(userId));
        userRepository.delete(user);
    }
}
