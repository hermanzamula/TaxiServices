package com.taxiservice.model;

import com.taxiservice.model.entity.Driver;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.TaxiDriverRepository;
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
    TaxiDriverRepository driverRepository;

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
    public long createDriver(String name,  String description, String site,
                             long city, List<String> numbers) {
        final Driver driver = new Driver(name, site, description);
        driver.getPhoneNumbers().addAll(phoneNumbersFromStrings(driver, numbers).toList());

        return driverRepository.save(driver).getId();
    }

    @Override
    public long createCity(String name, long country) {return 0;
    }

    @Override
    public long createCountry(String name) {
        return 0;
    }

    @Override
    public long driveType(String name, String description) {
       return 0;
    }
}
