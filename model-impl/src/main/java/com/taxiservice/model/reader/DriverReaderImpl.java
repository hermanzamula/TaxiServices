package com.taxiservice.model.reader;


import com.google.common.base.Predicate;
import com.taxiservice.model.entity.City;
import com.taxiservice.model.entity.DriveType;
import com.taxiservice.model.entity.TaxiDriver;
import com.taxiservice.model.entity.User;
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
    public List<DriverManagement.DriverInfo> readDriversByCity(long city, long region, long country) {
        return from(cityRepository.findOne(city).getTaxiDrivers())
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();
    }

    @Override
    public List<DriverManagement.DriverInfo> readDriversByDriveType(long driveType, long city, long region, long country) {
        DriveType type = driveTypeRepository.findOne(driveType);
        return from(type.getTaxiDrivers())
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();
    }

    @Override
    public List<DriverManagement.DriverInfo> readAll() {
        return from(taxiDriverRepository.findAll())
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();
    }

    @Override
    public List<DriverManagement.DriverInfo> readFavourites(long userId) {
        return from(userRepository.findOne(userId).getUserFavourites())
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();

    }

    @Override
    public List<DriverManagement.DriverInfo> readCityFavourites(long userId) {
        final User one = userRepository.findOne(userId);
        final City city = userPlaceRepository.findLastModified(one).getCity();
        return from(one.getUserFavourites())
                .filter(driverFromCity(city))
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
}
