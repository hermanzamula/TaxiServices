package com.taxiservice.model;


import com.taxiservice.model.repository.CityRepository;
import com.taxiservice.model.repository.TaxiDriverRepository;
import com.taxiservice.model.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.apache.cxf.common.util.StringUtils.isEmpty;

@Component
public class Validator {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final TaxiDriverRepository driverRepository;

    @Inject
    public Validator(UserRepository userRepository,
                     CityRepository cityRepository,
                     TaxiDriverRepository driverRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.driverRepository = driverRepository;
    }

    public boolean canCityBeAddedToPlaces(long actor, long city) {
        return !userRepository.findOne(actor).getUserPlaces().contains(cityRepository.findOne(city));
    }

    public boolean isUserCanBeCreated(String email) {
        return !isEmpty(email) && userRepository.findOneByEmail(email) != null;
    }

    public boolean canUserLikeDriver(long actor, long driver) {
        return !userRepository.findOne(actor).getFavourites().contains(driverRepository.findOne(driver));
    }

    public boolean canUserDislikeDriver(long actor, long taxiDriver) {
        return userRepository.findOne(actor).getFavourites().contains(driverRepository.findOne(taxiDriver));
    }

}
