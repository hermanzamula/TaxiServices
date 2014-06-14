package com.taxiservice.model.reader;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.taxiservice.model.Location;
import com.taxiservice.model.entity.Driver;
import com.taxiservice.model.entity.Located;
import com.taxiservice.model.entity.Trip;
import com.taxiservice.model.repository.DriverRepository;
import com.taxiservice.model.repository.PassengerRepository;
import com.taxiservice.model.repository.TripRepository;
import com.taxiservice.model.util.Transformers;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.LocationUtil.distanceMeters;

@Service
public class CarpoolReaderImpl implements CarpoolReader<Long> {

    @Inject
    private DriverRepository driverRepository;
    @Inject
    private PassengerRepository passengerRepository;
    @Inject
    private TripRepository tripRepository;

    @Override
    public Set<DriverLine> readDriversNear(Long actor, final Location location, final long searchRadius) {

        return from(driverRepository.findAll())
                .filter(withingLocation(location, searchRadius))
                .transform(Transformers.DRIVER_LINE_TRANSFORMER)
                .toSet();
    }

    @Override
    public Set<PassengerLine> readPassengers(Long actor, Long trip) {
        return from(passengerRepository.findByTrip(new Trip(trip)))
                .transform(Transformers.PASSENGER_TRANSFORMER)
                .toSet();
    }

    @Override
    public Set<TripLine> readTripsNear(Long actor, final Location location, final long searchRadius) {
        return from(tripRepository.findAll())
                .filter(withinStart(location, searchRadius))
                .transform(Transformers.TRIP_LINE_TRANSFORMER)
                .toSet();
    }

    private Predicate<Trip> withinStart(final Location location, final long searchRadius) {
        return new Predicate<Trip>() {
            @Override
            public boolean apply(Trip input) {
                final com.taxiservice.model.entity.Location start = input.getStart();
                return within(start, location, searchRadius);
            }
        };
    }

    @Override
    public Set<TripLine> readTrips(Long actor, Location from, final Location to, final long searchRadius) {
        return from(tripRepository.findAll())
                .filter(Predicates.and(withinStart(from, searchRadius), withinEnd(to, searchRadius)))
                .transform(Transformers.TRIP_LINE_TRANSFORMER)
                .toSet();
    }

    private Predicate<Trip> withinEnd(final Location to, final long searchRadius) {
        return new Predicate<Trip>() {
            @Override
            public boolean apply(Trip input) {
                return within(input.getEnd(), to, searchRadius);
            }
        };
    }

    @Override
    public Set<Feedback> readComments(final Long driver) {
        final Driver entity = driverRepository.findOne(driver);
        return from(entity.getComments())
                .transform(Transformers.COMMENTS_TRANSFORMER)
                .toSet();
    }

    private <T extends Located> Predicate<T> withingLocation(final Location location, final long searchRadius) {
        return new Predicate<T>() {
            @Override
            public boolean apply(T currentLocation) {
                return within(currentLocation.getLocation(), location, searchRadius);
            }
        };
    }

    private boolean within(com.taxiservice.model.entity.Location loc, Location location, long searchRadius) {
        return distanceMeters(location, new Location(loc.lng, loc.lat)) <= searchRadius;
    }
}
