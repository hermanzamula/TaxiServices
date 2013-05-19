package com.taxiservice.model.reader;


import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.taxiservice.model.entity.*;
import com.taxiservice.model.repository.*;
import com.taxiservice.model.writer.DriverManagement;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.find;
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
        UserPlace last = from(userPlaceRepository.findByUser(one)).last().get();
        return from(one.getUserFavourites())
                .filter(driverFromCity(last.getCity()))
                .transform(DRIVER_INFO_TRANSFORMER)
                .toImmutableList();
    }

    private FluentIterable<City> cityFromPlaceByUser(User user) {
       return from(user.getUserPlaces()).transform(new Function<UserPlace, City>() {
            @Override
            public City apply(UserPlace input) {
                 return input.getCity();
            }
        });
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
