package com.taxiservice.model.managment;


import com.taxiservice.model.AccessDenied;
import com.taxiservice.model.entity.City;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.entity.UserPlace;
import com.taxiservice.model.repository.CityRepository;
import com.taxiservice.model.repository.UserPlaceRepository;
import com.taxiservice.model.repository.UserRepository;
import com.taxiservice.model.writer.UserManagement;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;


@Service
public class UserManagementImpl implements UserManagement {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final UserPlaceRepository userPlaceRepository;

    public static final Logger LOGGER = Logger.getLogger(UserManagementImpl.class);

    @Inject
    public UserManagementImpl(UserRepository userRepository, CityRepository cityRepository, UserPlaceRepository userPlaceRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.userPlaceRepository = userPlaceRepository;
    }

    @Override
    public long createUser(UserInfo userInfo, String passwordHash) {
        checkNotNull(passwordHash);
        User user = new User(userInfo.firstName, userInfo.lastName, userInfo.email, passwordHash);
        City city = cityRepository.findOne(userInfo.place.city);
        saveUserPlace(user, city);
        return userRepository.save(user).getId();
    }

    private void saveUserPlace(User user, City city) {
        UserPlace place = new UserPlace(city, user, new Date());
        userPlaceRepository.save(place);
    }

    @Override
    public void updateUserInfo(long userId, UserInfo userInfo) {
        User user = checkNotNull(userRepository.findOne(userId));
        user.setFirstName(userInfo.firstName);
        user.setLastName(userInfo.lastName);

    }

    @Override
    public void updatePassword(long userId, String oldPassword, String passwordHash) {
        User user = checkNotNull(userRepository.findOne(userId));

        //TODO: implement password hash encoding
        if(!user.getPasswordHash().equals(oldPassword)) {
            throw new AccessDenied("Old password is invalid");
        }
        user.setPasswordHash(passwordHash);
    }

    @Override
    public void removeUser(long userId) {
        User user = checkNotNull(userRepository.findOne(userId));
        userRepository.delete(user);
    }
}
