package com.taxiservice.model.writer;


import com.taxiservice.model.AccessDenied;
import com.taxiservice.model.entity.City;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.entity.UserPlace;
import com.taxiservice.model.repository.CityRepository;
import com.taxiservice.model.repository.UserPlaceRepository;
import com.taxiservice.model.repository.UserRepository;
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
        if(userRepository.findOneByEmail(userInfo.email) != null) {
              throw new AccessDenied("User with email '" + userInfo.email + "' already registered");
        }
        User user = new User(userInfo.firstName, userInfo.lastName, userInfo.email, passwordHash);
        return updateUser(userInfo, user);
    }

    private long updateUser(UserInfo userInfo, User user) {
        City city = cityRepository.findOne(userInfo.place.city);
        if(city == null) {
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
        if(user.getUserPlaces().contains(city1)) {
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
    public void updatePassword(long userId, String oldPassword, String passwordHash) {
        User user = checkNotNull(userRepository.findOne(userId));

        //TODO: implement password hash encoding
        if (!user.getPasswordHash().equals(oldPassword)) {
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
