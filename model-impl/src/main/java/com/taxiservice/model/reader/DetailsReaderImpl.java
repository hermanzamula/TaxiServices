package com.taxiservice.model.reader;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.taxiservice.model.Location;
import com.taxiservice.model.entity.*;
import com.taxiservice.model.repository.DriverRepository;
import com.taxiservice.model.repository.PassengerRepository;
import com.taxiservice.model.repository.TripRepository;
import com.taxiservice.model.util.Transformers;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.util.Transformers.COMMENTS_TRANSFORMER;
import static com.taxiservice.model.util.Transformers.TRIP_LINE_TRANSFORMER;

/**
 * @author Herman Zamula
 */
@Service
public class DetailsReaderImpl implements DetailsReader<Long> {

    public static final Function<Car, CarInfo> CAR_INFO_TRANSFORMER = new Function<Car, CarInfo>() {
        @Override
        public CarInfo apply(Car input) {
            return new CarInfo<>(input.getId(), input.model, input.brand, input.description);
        }
    };
    @Inject
    private DriverRepository driverRepository;
    @Inject
    private PassengerRepository passengerRepository;
    @Inject
    private TripRepository tripRepository;

    @Override
    public DriverItem readDriver(Long actor, Long driver) {
        //TODO: validation
        final Driver entity = checkNotNull(driverRepository.findOne(driver));

        return new DriverItem<>(
                entity.getId(),
                toStringNumbers(entity.getPhoneNumbers()),
                entity.getUser().getFullName(),
                entity.getUser().getEmail(),
                entity.getDescription(),
                toModelLocation(entity.getCurrentLocation()),
                entity.getRate(),
                from(entity.getComments()).transform(COMMENTS_TRANSFORMER).toSet(),
                from(entity.getTrips()).transform(TRIP_LINE_TRANSFORMER).toSet(),
                from(entity.getCars()).transform(CAR_INFO_TRANSFORMER).toSet()
        );
    }

    private Location toModelLocation(com.taxiservice.model.entity.Location location) {
        return new Location(location.lng, location.lat);
    }

    private ImmutableSet<String> toStringNumbers(Collection<PhoneNumber> numbers) {
        return from(numbers).transform(Transformers.PHONE_NUMBER_TRANSFORMER).toSet();
    }

    @Override
    public PassengerItem readPassenger(Long actor, Long passenger) {

        final Passenger entity = checkNotNull(passengerRepository.findOne(passenger));

        return new PassengerItem<>(
                entity.getId(),
                entity.getUser().getFullName(),
                toStringNumbers(entity.getPhoneNumbers()),
                entity.getUser().getEmail(),
                entity.getRate(),
                entity.getDescription(),
                toModelLocation(entity.getCurrentLocation()),
                from(entity.getTrips()).transform(TRIP_LINE_TRANSFORMER).toSet()
        );
    }

    @Override
    public TripItem readTrip(Long actor, Long trip) {
        final Trip entity = checkNotNull(tripRepository.findOne(trip));
        return new TripItem<>(
                entity.getId(),
                entity.getDescription(),
                entity.getName(),
                Transformers.DRIVER_LINE_TRANSFORMER.apply(entity.getDriver()),
                CAR_INFO_TRANSFORMER.apply(entity.getCar()),
                from(entity.getSubscribedPassengers()).transform(Transformers.PASSENGER_TRANSFORMER).toSet(),
                toModelLocation(entity.getStart()),
                toModelLocation(entity.getEnd()),
                entity.getStartDate()
        );
    }

}
