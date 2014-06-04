package com.taxiservice.model.internal;


import com.taxiservice.model.AccessDenied;
import com.taxiservice.model.AuthenticationService;
import com.taxiservice.model.internal.entity.AccessToken;
import com.taxiservice.model.internal.entity.Application;
import com.taxiservice.model.internal.entity.User;
import com.taxiservice.model.internal.entity.UserPlace;
import com.taxiservice.model.internal.repository.AccessTokenRepository;
import com.taxiservice.model.internal.repository.ApplicationRepository;
import com.taxiservice.model.internal.repository.UserPlaceRepository;
import com.taxiservice.model.internal.repository.UserRepository;
import com.taxiservice.model.internal.util.Transformers;
import com.taxiservice.model.writer.UserManagement;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.math.BigInteger;
import java.security.SecureRandom;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;

@Transactional
@Service
public class UserCredentialsService implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserPlaceRepository userPlaceRepository;
    private final ApplicationRepository applicationRepository;
    private final AccessTokenRepository accessTokenRepository;


    private final PasswordEncoder encoder;

    private SecureRandom secureRandom = new SecureRandom();

    @Inject
    public UserCredentialsService(UserRepository userRepository, UserPlaceRepository userPlaceRepository, PasswordEncoder encoder, ApplicationRepository applicationRepository, AccessTokenRepository accessTokenRepository) {
        this.userRepository = userRepository;
        this.userPlaceRepository = userPlaceRepository;
        this.encoder = encoder;
        this.applicationRepository = applicationRepository;
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    public UserManagement.UserInfo checkCredentials(String email, String password) {

        checkNotNull(email, "User email is null");
        checkNotNull(password, "Password is null");

        User user = checkNotNull(userRepository.findOneByEmail(email), "User not found");

        if (!encoder.matches(password, user.getPasswordHash())) {
            throw new AccessDenied("Password doesn't match");
        }

        UserPlace place = from(userPlaceRepository.findByUser(user)).last().get();
        return new UserManagement.UserInfo(
                user.getId(),
                user.getLastName(),
                user.getEmail(),
                user.getFirstName(),
                new UserManagement.Place(
                        place.getCity().getId(),
                        place.getCity().getCountry().getId()
                ));
    }

    @Override
    public UserManagement.UserInfo readByEmail(String email) {
        final User user = userRepository.findOneByEmail(email);

        return new UserManagement.UserInfo(
                user.getId(),
                user.getLastName(),
                user.getEmail(),
                user.getFirstName(),
                null
        );
    }

    @Override
    public void createAccessToken(long user, long application) {

        final Application app = checkNotNull(applicationRepository.findOne(application));
        final User actor = checkNotNull(userRepository.findOne(user));

        final AccessToken token = new AccessToken(app, actor, createToken());

        actor.getTokens().add(token);
    }

    @Override
    public void removeAccessToken(long user, long application) {

        final AccessToken token = loadAccessToken(user, application);

        accessTokenRepository.delete(token);

    }

    @Override
    public AccessTokenInfo getAccessToken(long user, long application) {

        final AccessToken token = loadAccessToken(user, application);

        return Transformers.TO_TOKEN_INFO.apply(token);
    }

    private AccessToken loadAccessToken(long user, long application) {

        return checkNotNull(accessTokenRepository.findByUserAndApplication(user, application),
                String.format("Token not found for user '%d' and app '%d'", user, application));
    }

    private String createToken() {
        return new BigInteger(512, secureRandom).toString(32);
    }
}
