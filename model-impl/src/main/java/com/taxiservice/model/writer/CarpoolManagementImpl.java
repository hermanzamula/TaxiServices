package com.taxiservice.model.writer;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.taxiservice.model.entity.*;
import com.taxiservice.model.repository.DriverRepository;
import com.taxiservice.model.repository.PassengerRepository;
import com.taxiservice.model.repository.TripRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.ImmutableSet.of;
import static com.google.common.collect.Sets.difference;
import static com.taxiservice.model.util.Transformers.phoneNumbersFromStrings;

/**
 * @author Herman Zamula
 */
@Service
public class CarpoolManagementImpl implements CarpoolManagement<Long> {

    public static final Function<com.taxiservice.model.Location, Location> LOCATION_TRANSFORMER = new Function<com.taxiservice.model.Location, Location>() {
        @Override
        public Location apply(com.taxiservice.model.Location input) {
            final Location location = new Location();
            location.lat = input.lat;
            location.lng = input.lng;
            return location;
        }
    };
    @Inject
    private DriverRepository driverRepository;

    @Inject
    private TripRepository tripRepository;

    @Inject
    private PassengerRepository passengerRepository;

    @Override
    public void subscribe(@NotNull Long actor, @NotNull Long trip) {
        //TODO: validation
        final Trip entity = tripRepository.findOne(trip);
        entity.getSubscribedPassengers().add(passengerRepository.findByUser(new User(actor)));
        tripRepository.save(entity);
    }

    @Override
    public void removeSubscriber(@NotNull Long actor, Long trip, @NotNull Long passenger) {

        //TODO: validation
        final Trip entity = tripRepository.findOne(trip);
        final Passenger passengerEntity = passengerRepository.findOne(passenger);

        final ImmutableSet<Passenger> old = ImmutableSet.copyOf(entity.getSubscribedPassengers());
        entity.getSubscribedPassengers().clear();
        final Sets.SetView<Passenger> newPassengers = difference(old, of(passengerEntity));
        entity.getSubscribedPassengers().addAll(newPassengers);
        tripRepository.save(entity);
    }

    @Override
    public void rateDriver(@NotNull Long actor, @NotNull Long driver, int rate) {
        final Driver entity = driverRepository.findOne(driver);
        entity.setRatedCount(entity.getRatedCount() + 1);
        entity.setRate((entity.getRate() + rate) / entity.getRatedCount());
        driverRepository.save(entity);
    }

    @Override
    public void leaveComment(@NotNull Long actor, @NotNull Long driver, String message) {
        final Driver one = driverRepository.findOne(driver);
        one.getComments().add(new Comment(message, new User(actor), one));
        driverRepository.save(one);
    }

    @Override
    public void ratePassenger(@NotNull Long actor, @NotNull Long passenger, int rate) {
        final Passenger entity = passengerRepository.findOne(passenger);
        entity.setRatedCount(entity.getRatedCount() + 1);
        entity.setRate((entity.getRate() + rate) / entity.getRatedCount());
        passengerRepository.save(entity);
    }

    @Override
    public Long createTrip(@NotNull Long actor, @NotNull Long car, TripInfo tripInfo) {
        final Trip trip = new Trip();
        trip.setDescription(tripInfo.description);
        trip.setDriver(checkNotNull(driverRepository.findByUser(new User(actor))));
        trip.setEnd(LOCATION_TRANSFORMER.apply(checkNotNull(tripInfo.end)));
        trip.setStart(LOCATION_TRANSFORMER.apply(checkNotNull(tripInfo.start)));
        trip.setStartDate(tripInfo.startDate);
        trip.setPassengersLimit(tripInfo.passengersLimit);
        trip.setName(tripInfo.name);
        trip.setCar(new Car(car));
        return tripRepository.save(trip).getId();
    }

    @Override
    public Long createDriver(@NotNull Long actor, final DriverInfo driverInfo) {
        final Driver driver = new Driver(driverInfo.description, phoneNumbersFromStrings(driverInfo.phones));

        driver.setUser(new User(actor));
        final Long id = driverRepository.save(driver).getId();
        driver.getCars().addAll(from(driverInfo.cars).transform(transformDriverCars(id)).toSet());
        return driverRepository.save(driver).getId();
    }

    private Function<CarInfo, Car> transformDriverCars(final Long driver) {
        return new Function<CarInfo, Car>() {
            @Override
            public Car apply( CarInfo input) {
                return new Car(input.brand, input.description, input.model, new Driver(driver));
            }
        };
    }

    @Override
    public Long createPassenger(@NotNull Long actor, PassengerInfo passengerInfo) {
        final Passenger passenger = new Passenger(new User(actor), phoneNumbersFromStrings(passengerInfo.phones));
        return passengerRepository.save(passenger).getId();
    }
}
