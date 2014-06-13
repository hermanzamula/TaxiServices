package com.taxiservice.model;

import com.google.common.collect.ImmutableSet;
import com.taxiservice.model.entity.Driver;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.DriverRepository;
import com.taxiservice.model.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.taxiservice.model.util.Transformers.phoneNumbersFromStrings;

@Service
public class PredefinedDataCreatorImpl implements PredefinedDataCreator {

    @Inject
    UserRepository userRepository;
    @Inject
    DriverRepository driverRepository;

    @Inject
    PasswordEncoder encoder;

    @Override
    public long createAdmin(String name, String lastName, String email, String password) {
        String passwordHash = encoder.encode(password);
        final User user = new User(name, lastName, email, passwordHash);
        user.isAdmin = true;
        return userRepository.save(user).getId();
    }

    @Override
    public long createUser(String name, String lastName, String email, String password) {

        String passwordHash = encoder.encode(password);
        final User user = new User(name, lastName, email, passwordHash);
        return userRepository.save(user).getId();
    }

    @Override
    public long createDriver(String name, String description, String site,
                             long city, List<String> numbers) {

        final Driver driver = new Driver(description, phoneNumbersFromStrings(ImmutableSet.copyOf(numbers)));

        return driverRepository.save(driver).getId();
    }
}
