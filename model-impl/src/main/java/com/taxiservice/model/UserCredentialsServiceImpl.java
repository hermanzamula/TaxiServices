package com.taxiservice.model;


import com.taxiservice.model.entity.User;
import com.taxiservice.model.entity.UserPlace;
import com.taxiservice.model.repository.UserPlaceRepository;
import com.taxiservice.model.repository.UserRepository;
import com.taxiservice.model.writer.UserManagement;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService{

    private final UserRepository userRepository;
    private final UserPlaceRepository userPlaceRepository;

    @Inject
    public UserCredentialsServiceImpl(UserRepository userRepository, UserPlaceRepository userPlaceRepository) {
        this.userRepository = userRepository;
        this.userPlaceRepository = userPlaceRepository;
    }

    @Override
    public UserManagement.UserInfo checkCredentials(String email, String passwordHash) {
        User user = checkNotNull(userRepository.findOneByEmailAndPasswordHash(email, passwordHash), "User not found");
        UserPlace place = from(userPlaceRepository.findByUser(user)).last().get();
        return new UserManagement.UserInfo(user.getId(), user.getLastName(), user.getEmail(), user.getFirstName(), new UserManagement.Place(
                place.getCity().getId(),
                place.getCity().getCountry().getId()
        ));
    }
}
