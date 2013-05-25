package com.taxiservice.model.reader;


import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.taxiservice.model.entity.*;
import com.taxiservice.model.repository.*;
import com.taxiservice.model.writer.DriverManagement;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.util.Transformers.DRIVER_INFO_TRANSFORMER;

@Service
public class DriverReaderImpl implements DriverReader {

    @Inject
    private UserRepository userRepository;
    @Inject
    private TaxiDriverRepository taxiDriverRepository;
    @Inject
    private CityRepository cityRepository;
    @Inject
    private DriveTypeRepository driveTypeRepository;
    @Inject
    private UserPlaceRepository userPlaceRepository;

    @Override
    public List<DriverManagement.DriverDetails> readDriversByCity(long city) {

        return from(cityRepository.findOne(city).getTaxiDrivers())
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();
    }

    @Override
    public List<DriverManagement.DriverDetails> readDriversByDriveType(long driveType, long city) {
        DriveType type = driveTypeRepository.findOne(driveType);
        List<TaxiDriver> drivers = Lists.newArrayList();
        for(Price driver: type.getTaxiDrivers()) {
            drivers.add(driver.getTaxiDriver());
        }
        return from(drivers).transform(DRIVER_INFO_TRANSFORMER).toImmutableList();
    }

    @Override
    public List<DriverManagement.DriverDetails> readAll() {

        return from(taxiDriverRepository.findAll())
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();
    }

    @Override
    public List<DriverManagement.DriverDetails> readFavourites(long userId) {

        return from(userRepository.findOne(userId).getUserFavourites())
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();

    }

    @Override
    public List<DriverManagement.DriverDetails> readCurrentCityFavourites(long userId) {
        final User user = userRepository.findOne(userId);
        UserPlace last = from(userPlaceRepository.findByUser(user)).last().get();

        return from(user.getUserFavourites())
                .filter(driverFromCity(last.getCity()))
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();
    }

    @Override
    public List<DriverManagement.DriverDetails> readCityFavourites(long actor, long cityId) {

        return from(userRepository.findOne(actor).getUserFavourites())
                .filter(driverFromCity(cityId))
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();
    }

    private Predicate<TaxiDriver> driverFromCity(final City city) {
        return new Predicate<TaxiDriver>() {
            @Override
            public boolean apply(TaxiDriver input) {
                return input.getCity().getId().equals(city.getId());
            }
        };
    }

    private Predicate<TaxiDriver> driverFromCity(final long city) {
        return driverFromCity(cityRepository.findOne(city));
    }

}
