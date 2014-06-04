package com.taxiservice.model.internal.reader;


import com.google.common.base.Predicate;
import com.taxiservice.model.internal.entity.*;
import com.taxiservice.model.internal.repository.*;
import com.taxiservice.model.internal.util.Transformers;
import com.taxiservice.model.reader.DriverReader;
import com.taxiservice.model.writer.DriverManagement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.tryFind;
import static com.google.common.collect.Lists.newArrayList;
import static com.taxiservice.model.internal.util.Transformers.TO_DRIVER_DETAILS;
import static com.taxiservice.model.internal.util.Transformers.TO_FEEDBACK;

@Transactional(readOnly = true)
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
                .transform(TO_DRIVER_DETAILS)
                .toList();
    }

    @Override
    public List<DriverManagement.DriverDetails> readDriversByDriveType(long driveType, long city) {
        DriveType type = driveTypeRepository.findOne(driveType);
        List<TaxiDriver> drivers = newArrayList();
        for (Price driver : type.getPrices()) {
            drivers.add(driver.getTaxiDriver());
        }
        return from(drivers).transform(TO_DRIVER_DETAILS).toList();
    }

    @Override
    public List<DriverManagement.DriverDetails> readAll() {
        return from(taxiDriverRepository.findAll())
                .transform(TO_DRIVER_DETAILS)
                .toList();
    }

    @Override
    public List<DriverManagement.DriverDetails> readFavourites(long userId) {
        return from(userRepository.findOne(userId).getFavourites())
                .transform(TO_DRIVER_DETAILS)
                .toList  ();
    }

    @Override
    public List<DriverManagement.DriverDetails> readCurrentCityFavourites(long userId) {
        final User user = userRepository.findOne(userId);
        UserPlace last = from(userPlaceRepository.findByUser(user)).last().get();

        return from(user.getFavourites())
                .filter(driverByCity(last.getCity()))
                .transform(TO_DRIVER_DETAILS)
                .toList  ();
    }

    @Override
    public List<DriverManagement.DriverDetails> readCityFavourites(long actor, long cityId) {
        return from(userRepository.findOne(actor).getFavourites())
                .filter(driverFromCity(cityId))
                .transform(TO_DRIVER_DETAILS)
                .toList();
    }

    @Override
    public List<Feedback> readComments(long driver) {
        return from(taxiDriverRepository.findOne(driver).getComments())
                .transform(TO_FEEDBACK)
                .toList();
    }

    @Override
    public List<DriverLine> readShortInfo(long city) {
        return from(cityRepository.findOne(city).getTaxiDrivers())
                .transform(Transformers.TO_DRIVE_LINE)
                .toList();
    }

    @Override
    public DriverManagement.DriverDetails readDetails(long id) {
        final TaxiDriver driver = checkNotNull(taxiDriverRepository.findOne(id));
        return TO_DRIVER_DETAILS.apply(driver);
    }

    private Predicate<TaxiDriver> driverByCity(final City city) {
        return new Predicate<TaxiDriver>() {
            @Override
            public boolean apply(TaxiDriver input) {
                return tryFind(input.getCities(), new Predicate<City>() {
                    @Override
                    public boolean apply(City input) {
                        return input.equals(city);
                    }
                }).isPresent();
            }
        };
    }

    private Predicate<TaxiDriver> driverFromCity(final long city) {
        return driverByCity(cityRepository.findOne(city));
    }

}
