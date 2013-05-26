package com.taxiservice.model.writer;


import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.taxiservice.model.AccessDenied;
import com.taxiservice.model.entity.*;
import com.taxiservice.model.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.util.Transformers.phoneNumbersFromStrings;


@Service
public class DriverManagementImpl implements DriverManagement {


    private final TaxiDriverRepository driverRepository;
    private final UserRepository userRepository;
    private final PriceRepository priceRepository;
    private final CityRepository cityRepository;
    private final DriveTypeRepository driveTypeRepository;

    @Inject
    public DriverManagementImpl(TaxiDriverRepository driverRepository, UserRepository userRepository, PriceRepository priceRepository, CityRepository cityRepository, DriveTypeRepository driveTypeRepository) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
        this.priceRepository = priceRepository;
        this.cityRepository = cityRepository;
        this.driveTypeRepository = driveTypeRepository;
    }

    @Override
    public long createDriver(DriverInfo driverDetails) {
        final ImmutableList<City> cities = from(driverDetails.cities).transform(toCity()).toImmutableList();
        final TaxiDriver driver = new TaxiDriver(driverDetails.name, driverDetails.site, driverDetails.description);
        final List<String> phones = driverDetails.phones;
        driver.getPrices().addAll(from(driverDetails.prices).transform(toPrice(driver)).toImmutableSet());
        driver.getPhoneNumbers().addAll(phoneNumbersFromStrings(driver, phones).toImmutableList());
        driver.getCities().addAll(cities);
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
        //TODO: Create validator
        if (actor.getFavourites().contains(taxiDriver)) {
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
        if (!actor.getFavourites().remove(taxiDriver)) {
            return;
        }
        taxiDriver.setRate(taxiDriver.getRate() - 1);
        save(taxiDriver, actor);
    }

    @Override
    public void updateDriverInfo(long driver, DriverInfo driverDetails) {
        final TaxiDriver taxiDriver = driverRepository.findOne(driver);
        taxiDriver.getPhoneNumbers().clear();
        taxiDriver.getPhoneNumbers().addAll(phoneNumbersFromStrings(taxiDriver, driverDetails.phones).toImmutableSet());
        taxiDriver.setName(driverDetails.name);
        taxiDriver.setSite(driverDetails.site);
        taxiDriver.getPrices().clear();
        taxiDriver.getPrices().addAll(from(driverDetails.prices).transform(toPrice(taxiDriver)).toImmutableSet());
        taxiDriver.getCities().addAll(from(driverDetails.cities).transform(toCity()).toImmutableSet());
        taxiDriver.setDescription(driverDetails.description);
    }

    @Override
    public void comment(long user, long driver, String message) {
        checkNotNull(message);
        final TaxiDriver one = driverRepository.findOne(driver);
        one.getComments().add(newComment(user, message, one));
        driverRepository.save(one);
    }

    private Comment newComment(long user, String message, TaxiDriver driver) {
        final String email = userRepository.findOne(user).getEmail();
        final Comment comment = new Comment(message, email, driver);
        comment.setDate(new Date());
        return comment;
    }

    private Function<DriverPrice, Price> toDriveType() {
        return new Function<DriverPrice, Price>() {
            @Override
            public Price apply(DriverPrice input) {
                return priceRepository.findOne(input.id);
            }
        };
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
                return new Price(driver, driveTypeRepository.findOne(input.driveType), new PriceInfo((long) input.min, (long) input.max, input.description));
            }
        };
    }

}
