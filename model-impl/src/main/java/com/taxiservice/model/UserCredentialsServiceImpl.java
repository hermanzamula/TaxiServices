package com.taxiservice.model;


import com.taxiservice.model.entity.User;
import com.taxiservice.model.entity.UserPlace;
import com.taxiservice.model.repository.UserPlaceRepository;
import com.taxiservice.model.repository.UserRepository;
import com.taxiservice.model.writer.UserManagement;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.Access;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService{

    private final UserRepository userRepository;
    private final UserPlaceRepository userPlaceRepository;
    private final PasswordEncoder encoder;

    @Inject
    public UserCredentialsServiceImpl(UserRepository userRepository, UserPlaceRepository userPlaceRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userPlaceRepository = userPlaceRepository;
        this.encoder = encoder;
    }

    @Override
    public UserManagement.UserInfo checkCredentials(String email, String password) {

        checkNotNull(email, "User email is null");
        checkNotNull(password, "Password is null");

        User user = checkNotNull(userRepository.findOneByEmail(email), "User not found");

        if(!encoder.matches(password, user.getPasswordHash())) {
            throw new AccessDenied("Password doesn't match");
        }

        UserPlace place = from(userPlaceRepository.findByUser(user)).last().get();
        return new UserManagement.UserInfo(user.getId(), user.getLastName(), user.getEmail(), user.getFirstName(), new UserManagement.Place(
                place.getCity().getId(),
                place.getCity().getCountry().getId()
        ));
    }
}
