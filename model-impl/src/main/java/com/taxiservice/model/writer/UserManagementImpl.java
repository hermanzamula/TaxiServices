package com.taxiservice.model.writer;


import com.taxiservice.model.AccessDenied;
import com.taxiservice.model.Validator;
import com.taxiservice.model.entity.City;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.entity.UserPlace;
import com.taxiservice.model.repository.CityRepository;
import com.taxiservice.model.repository.UserPlaceRepository;
import com.taxiservice.model.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;


@Service
public class UserManagementImpl implements UserManagement {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final UserPlaceRepository userPlaceRepository;
    private final Validator validator;
    private final PasswordEncoder encoder;

    @Inject
    public UserManagementImpl(UserRepository userRepository,
                              CityRepository cityRepository,
                              UserPlaceRepository userPlaceRepository,
                              Validator validator, PasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.userPlaceRepository = userPlaceRepository;
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
        City city = cityRepository.findOne(userInfo.place.city);
        if (city == null) {
            return saveUser(user);
        }
        user.getUserPlaces().add(city);
        final Long id = saveUser(user);
        saveUserPlace(user, city);
        return id;
    }

    private Long saveUser(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public void addUserToPlace(long actor, long city) {
        final City city1 = checkNotNull(cityRepository.findOne(city));
        final User user = checkNotNull(userRepository.findOne(actor));
        if (!validator.canCityBeAddedToPlaces(actor, city)) {
            throw new IllegalStateException("City already added to user place");
        }
        user.getUserPlaces().add(city1);
        userRepository.save(user);
        saveUserPlace(user, city1);
    }

    private void saveUserPlace(User user, City city) {
        final UserPlace place = userPlaceRepository.findByUserAndCity(user, city);
        place.setLastModification(new Date());
        userPlaceRepository.save(place);
    }

    @Override
    public void updateUserInfo(long userId, UserInfo userInfo) {
        User user = checkNotNull(userRepository.findOne(userId));
        user.setFirstName(userInfo.firstName);
        user.setLastName(userInfo.lastName);
        updateUser(userInfo, user);
    }

    @Override
    public void updatePassword(long userId, String oldPassword, String password) {
        User user = checkNotNull(userRepository.findOne(userId));

        //TODO: implement password hash encoding
        if (!user.getPasswordHash().equals(encoder.encode(oldPassword))) {
            throw new AccessDenied("Old password is invalid");
        }

        final String passwordHash = encoder.encode(password);
        user.setPasswordHash(passwordHash);
    }

    @Override
    public void removeUser(long userId) {
        User user = checkNotNull(userRepository.findOne(userId));
        userRepository.delete(user);
    }
}
