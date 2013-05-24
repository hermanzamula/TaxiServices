package com.taxiservice.model;

import com.taxiservice.model.entity.*;
import com.taxiservice.model.repository.*;
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
    CityRepository cityRepository;
    @Inject
    DriveTypeRepository typeRepository;
    @Inject
    CountryRepository countryRepository;

    @Override
    public long createAdmin(String name, String lastName, String email, String password) {
        final User user = new User(name, lastName, email, password);
        user.setAdmin(true);
        return userRepository.save(user).getId();
    }

    @Override
    public long createUser(String name, String lastName, String email, String password) {
        final User user = new User(name, lastName, email, password);
        return userRepository.save(user).getId();
    }

    @Override
    public long createDriver(String name, String description, String site, long city, List<String> numbers, List<HasDriveType> driveTypes) {
        final TaxiDriver driver = new TaxiDriver(name, cityRepository.findOne(city), site, description);
        driver.getPhoneNumbers().addAll(phoneNumbersFromStrings(driver, numbers).toImmutableSet());
        for (HasDriveType type : driveTypes) {
            final DriveType one = typeRepository.findOne(type.driveType);
            final TaxiDriverHasDriveType hasType = new TaxiDriverHasDriveType(driver, one, new Price(type.minVal, type.maxVal, type.description));
            driver.getPrices().add(hasType);
        }
        return driverRepository.save(driver).getId();
    }

    @Override
    public long createCity(String name, long country) {
        final City city = new City(name, countryRepository.findOne(country));
        return cityRepository.save(city).getId();
    }

    @Override
    public long createCountry(String name) {
        return countryRepository.save(new Country(name)).getId();
    }

    @Override
    public long driveType(String name, String description) {
        final DriveType type = new DriveType();
        type.setName(name);
        type.setDescription(description);
        return typeRepository.save(type).getId();
    }
}
