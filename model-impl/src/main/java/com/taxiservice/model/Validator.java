package com.taxiservice.model;


import com.taxiservice.model.repository.TaxiDriverRepository;
import com.taxiservice.model.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.apache.cxf.common.util.StringUtils.isEmpty;

@Component
public class Validator {

    private final UserRepository userRepository;
    private final TaxiDriverRepository driverRepository;

    @Inject
    public Validator(UserRepository userRepository,
                     TaxiDriverRepository driverRepository) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
    }


    public boolean isUserCanBeCreated(String email) {
        return !isEmpty(email) && userRepository.findOneByEmail(email) != null;
    }

    public boolean canUserLikeDriver(long actor, long driver) {
        return true;
    }

    public boolean canUserDislikeDriver(long actor, long taxiDriver) {
        return true;
    }

}
