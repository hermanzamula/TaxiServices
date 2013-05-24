package com.taxiservice.model.writer;


import com.google.common.base.Function;
import com.taxiservice.model.AccessDenied;
import com.taxiservice.model.entity.City;
import com.taxiservice.model.entity.DriveType;
import com.taxiservice.model.entity.TaxiDriver;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.CityRepository;
import com.taxiservice.model.repository.DriveTypeRepository;
import com.taxiservice.model.repository.TaxiDriverRepository;
import com.taxiservice.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.util.Transformers.phoneNumbersFromStrings;


@Service
public class DriverManagementImpl implements DriverManagement {

    private final TaxiDriverRepository driverRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    DriveTypeRepository driveTypeRepository;

    @Inject
    public DriverManagementImpl(TaxiDriverRepository driverRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public long createDriver(DriverDetails driverDetails) {
        final City city = cityRepository.findOne(driverDetails.city);
        final TaxiDriver driver = new TaxiDriver(driverDetails.name, city, driverDetails.site, driverDetails.description);
        final List<String> phones = driverDetails.phones;
        driver.getPhoneNumbers().clear();
        driver.getPhoneNumbers().addAll(phoneNumbersFromStrings(driver, phones).toImmutableList());
        return driverRepository.save(driver).getId();
    }

    @Override
    public void removeDriver(long user, long driver) {
        if (!userRepository.findOne(user).isAdmin()) {
            throw new AccessDenied("Only admin can remove Taxi Driver");
        }
        driverRepository.delete(driver);
    }

    @Override
    public void likeDriver(long user, long driver) {
        final TaxiDriver taxiDriver = driverRepository.findOne(driver);
        final User actor = userRepository.findOne(user);
        actor.getUserFavourites().add(taxiDriver);
        taxiDriver.setRate(taxiDriver.getRate() + 1);
        save(taxiDriver, actor);
    }

    @Override
    public void dislikeDriver(long user, long driver) {
        final TaxiDriver taxiDriver = driverRepository.findOne(driver);
        final User actor = userRepository.findOne(user);
        actor.getUserFavourites().remove(taxiDriver);
        taxiDriver.setRate(taxiDriver.getRate() - 1);
        save(taxiDriver, actor);
    }

    @Override
    public void updateDriverInfo(long driver, DriverDetails driverDetails) {
        final TaxiDriver taxiDriver = driverRepository.findOne(driver);
        taxiDriver.getPhoneNumbers().clear();
        taxiDriver.getPhoneNumbers().addAll(phoneNumbersFromStrings(taxiDriver, driverDetails.phones).toImmutableSet());
        taxiDriver.setName(driverDetails.name);
        taxiDriver.setSite(driverDetails.site);
        taxiDriver.getDriveTypes().clear();
        taxiDriver.getDriveTypes().addAll(from(driverDetails.driveTypes).transform(toDriveType()).toImmutableSet());
        taxiDriver.setDescription(driverDetails.description);
    }

    private Function<Type, DriveType> toDriveType() {
        return new Function<Type, DriveType>() {
            @Override
            public DriveType apply(Type input) {
                return driveTypeRepository.findOne(input.id);
            }
        };
    }

    private void save(TaxiDriver taxiDriver, User actor) {
        userRepository.save(actor);
        driverRepository.save(taxiDriver);
    }

}
