package com.taxiservice.model;


import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.UserRepository;
import com.taxiservice.model.writer.UserManagement;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Inject
    public UserCredentialsServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserData checkCredentials(String email, String password) {

        checkNotNull(email, "User email is null");
        checkNotNull(password, "Password is null");

        User user = checkNotNull(userRepository.findOneByEmail(email), "User not found");

        if(!encoder.matches(password, user.getPasswordHash())) {
            throw new AccessDenied("Password doesn't match");
        }

        return new UserData<>(user.getId(), user.getLastName(), user.getEmail(), user.getFirstName());
    }
}
