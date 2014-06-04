package com.taxiservice.model.internal.writer;


import com.google.common.base.Function;
import com.taxiservice.model.AccessDenied;
import com.taxiservice.model.internal.Validator;
import com.taxiservice.model.internal.entity.*;
import com.taxiservice.model.internal.repository.CityRepository;
import com.taxiservice.model.internal.repository.DriveTypeRepository;
import com.taxiservice.model.internal.repository.TaxiDriverRepository;
import com.taxiservice.model.internal.repository.UserRepository;
import com.taxiservice.model.writer.DriverManagement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.internal.util.Transformers.phoneNumbersFromStrings;


@Transactional
@Service
public class DriverManagementImpl implements DriverManagement {


    private final TaxiDriverRepository driverRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final DriveTypeRepository driveTypeRepository;
    private final Validator validator;

    @Inject
    public DriverManagementImpl(TaxiDriverRepository driverRepository,
                                UserRepository userRepository,
                                CityRepository cityRepository,
                                DriveTypeRepository driveTypeRepository,
                                Validator validator) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.driveTypeRepository = driveTypeRepository;
        this.validator = validator;
    }

    @Override
    public long createDriver(DriverInfo driverDetails) {
        final TaxiDriver driver = new TaxiDriver(driverDetails.name, driverDetails.site, driverDetails.description);
        driver.setEmail(driverDetails.email);
        updateCollectionsInfo(driverDetails, driver);
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
        if (!validator.canUserLikeDriver(user, driver)) {
            return;
        }
        actor.getFavourites().add(taxiDriver);
        taxiDriver.setRate(taxiDriver.getRate() + 1);
        save(taxiDriver, actor);
    }

    @Override
    public void dislikeDriver(long user, long driver) {
        final TaxiDriver taxiDriver = driverRepository.findOne(driver);
        final User actor = userRepository.findOne(user);
        if (!validator.canUserDislikeDriver(user, driver)) {
            return;
        }
        taxiDriver.setRate(taxiDriver.getRate() - 1);
        save(taxiDriver, actor);
    }

    @Override
    public void updateDriverInfo(long driver, DriverInfo driverDetails) {
        final TaxiDriver taxiDriver = driverRepository.findOne(driver);
        taxiDriver.setName(driverDetails.name);
        taxiDriver.setSite(driverDetails.site);
        taxiDriver.setDescription(driverDetails.description);
        taxiDriver.setEmail(driverDetails.email);
        updateCollectionsInfo(driverDetails, taxiDriver);
    }

    private void updateCollectionsInfo(DriverInfo driverDetails, TaxiDriver taxiDriver) {
        taxiDriver.getPhoneNumbers().clear();
        taxiDriver.getPhoneNumbers().addAll(phoneNumbersFromStrings(taxiDriver, driverDetails.phones).toList());
        taxiDriver.getPrices().clear();
        taxiDriver.getPrices().addAll(from(driverDetails.prices).transform(toPrice(taxiDriver)).toList());
        taxiDriver.getCities().clear();
        taxiDriver.getCities().addAll(from(driverDetails.cities).transform(toCity()).toList());
    }

    @Override
    public void comment(long user, long driver, String message) {
        checkNotNull(message);
        final TaxiDriver taxiDriver = driverRepository.findOne(driver);
        taxiDriver.getComments().add(newComment(user, message, taxiDriver));
        driverRepository.save(taxiDriver);
    }

    private Comment newComment(long user, String message, TaxiDriver driver) {
        final String email = userRepository.findOne(user).getEmail();
        final Comment comment = new Comment(message, email, driver);
        comment.setDate(new Date());
        return comment;
    }

    private void save(TaxiDriver taxiDriver, User actor) {
        userRepository.save(actor);
        driverRepository.save(taxiDriver);
    }

    private Function<Long, City> toCity() {
        return new Function<Long, City>() {
            @Override
            public City apply(Long input) {
                return cityRepository.findOne(input);
            }
        };
    }

    private Function<PriceList, Price> toPrice(final TaxiDriver driver) {
        return new Function<PriceList, Price>() {
            @Override
            public Price apply(PriceList input) {
                return new Price(driver, driveTypeRepository.findOne(input.driveType), new PriceInfo(BigDecimal.valueOf(input.min), BigDecimal.valueOf(input.max), input.description));
            }
        };
    }

}
