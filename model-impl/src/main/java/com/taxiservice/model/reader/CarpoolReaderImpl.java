package com.taxiservice.model.reader;


import com.taxiservice.model.Location;
import com.taxiservice.model.repository.TaxiDriverRepository;
import com.taxiservice.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
public class CarpoolReaderImpl implements CarpoolReader<Long> {

    @Inject
    private UserRepository userRepository;
    @Inject
    private TaxiDriverRepository taxiDriverRepository;

    @Override
    public Set<DriverLine> readDriversNear(Long actor, Location location, long searchRadius) {
        return null;
    }

    @Override
    public Set<PassengerLine> readPassengers(Long actor, Long trip) {
        return null;
    }

    @Override
    public Set<TripLine> readTripsNear(Long actor, Location location, long searchRadius) {
        return null;
    }

    @Override
    public Set<TripLine> readTrips(Long actor, Location from, Location to, long searchRadius) {
        return null;
    }

    @Override
    public Set<Feedback> readComments(Long driver) {
        return null;
    }
}
