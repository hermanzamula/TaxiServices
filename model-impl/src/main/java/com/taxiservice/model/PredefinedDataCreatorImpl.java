package com.taxiservice.model;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.taxiservice.model.entity.Driver;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.DriverRepository;
import com.taxiservice.model.repository.UserRepository;
import com.taxiservice.model.util.Transformers;
import com.taxiservice.model.writer.CarpoolManagement;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.util.Transformers.phoneNumbersFromStrings;
import static com.taxiservice.model.util.Transformers.transformDriverCars;

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
    public long createDriver(long user, String description,
                             List<String> numbers, Set<CarpoolManagement.CarInfo> cars) {

        final Driver driver = new Driver(null);
        driver.setUser(new User(user));
        driverRepository.save(driver);
        driver.getCars().addAll(from(cars).transform(transformDriverCars(driver.getId())).toSet());
        driver.setDescription(description);
        driver.getPhoneNumbers().addAll(phoneNumbersFromStrings(ImmutableSet.copyOf(numbers)));

        return driverRepository.save(driver).getId();
    }
}
