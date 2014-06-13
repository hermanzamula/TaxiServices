package com.taxiservice.model.reader;

import org.springframework.stereotype.Service;

/**
 * @author Herman Zamula
 */
@Service
public class DetailsReaderImpl implements DetailsReader<Long> {
    @Override
    public DriverItem readDriver(Long actor, Long driver) {
        return null;
    }

    @Override
    public PassengerItem readPassenger(Long actor, Long passenger) {
        return null;
    }

    @Override
    public TripItem readTrip(Long actor, Long trip) {
        return null;
    }
}
