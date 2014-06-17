package com.taxiservice.model.util;


import com.google.common.base.Function;
import com.taxiservice.model.Location;
import com.taxiservice.model.entity.*;
import com.taxiservice.model.reader.CarpoolReader;
import com.taxiservice.model.reader.NotificationReader;
import com.taxiservice.model.writer.CarpoolManagement;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.size;

@Component
public class Transformers {

    public static final Function<PhoneNumber, String> PHONE_NUMBER_TRANSFORMER = new Function<PhoneNumber, String>() {
        @Override
        public String apply(PhoneNumber input) {
            return input.number;
        }
    };
    public static final Function<Passenger, CarpoolReader.PassengerLine<Long>> PASSENGER_TRANSFORMER = new Function<Passenger, CarpoolReader.PassengerLine<Long>>() {
        @Override
        public CarpoolReader.PassengerLine<Long> apply(Passenger input) {
            return new CarpoolReader.PassengerLine<>(input.getId(),
                    input.getUser().getFullName(),
                    input.getDescription(),
                    new Location(input.getCurrentLocation().lng, input.getCurrentLocation().lat),
                    input.getRate(),
                    from(input.getPhoneNumbers()).transform(PHONE_NUMBER_TRANSFORMER).toSet()
            );
        }
    };
    public static final Function<Driver, CarpoolReader.DriverLine<Long>> DRIVER_LINE_TRANSFORMER = new Function<Driver, CarpoolReader.DriverLine<Long>>() {
        @Override
        public CarpoolReader.DriverLine<Long> apply(Driver input) {
            return new CarpoolReader.DriverLine<>(input.getId(),
                    input.getUser().getFullName(),
                    input.getRate(),
                    from(input.getPhoneNumbers()).transform(PHONE_NUMBER_TRANSFORMER).toList());
        }
    };
    public static final Function<Trip, CarpoolReader.TripLine<Long>> TRIP_LINE_TRANSFORMER = new Function<Trip, CarpoolReader.TripLine<Long>>() {
        @Override
        public CarpoolReader.TripLine<Long> apply(Trip input) {
            int passengersLimit = input.getPassengersLimit();
            return new CarpoolReader.TripLine<>(input.getId(),
                    input.getName(),
                    input.getDescription(),
                    passengersLimit,
                    passengersLimit - size(input.getSubscribedPassengers()),
                    new Location(input.getStart().lng, input.getStart().lat),
                    new Location(input.getEnd().lng, input.getEnd().lat),
                    input.getStartDate());
        }
    };
    public static final Function<Message, NotificationReader.NotificationLine> NOTIFICATION_TRANSFORMER = new Function<Message, NotificationReader.NotificationLine>() {
        @Override
        public NotificationReader.NotificationLine apply(Message input) {
            return new NotificationReader.NotificationLine<>(input.getId(), input.getHeading(), input.getBody(), input.getSender().getFullName(), input.getRecipient().getFullName(), input.getDate());
        }
    };
    public static final Function<Comment, CarpoolReader.Feedback<Long>> COMMENTS_TRANSFORMER = new Function<Comment, CarpoolReader.Feedback<Long>>() {
        @Override
        public CarpoolReader.Feedback<Long> apply(Comment input) {
            return new CarpoolReader.Feedback<>(input.getUser().getFullName(), input.getDriver().getId(), input.getMessage(), input.getDate());
        }
    };
    public static final Function<Location, com.taxiservice.model.entity.Location> LOCATION_TRANSFORMER = new Function<Location, com.taxiservice.model.entity.Location>() {
        @Override
        public com.taxiservice.model.entity.Location apply(Location input) {
            final com.taxiservice.model.entity.Location location = new com.taxiservice.model.entity.Location();
            location.lat = input.lat;
            location.lng = input.lng;
            return location;
        }
    };

    public static Set<PhoneNumber> phoneNumbersFromStrings(Set<String> phones) {
        return from(phones)
                .transform(new Function<String, PhoneNumber>() {
                    @Override
                    public PhoneNumber apply(String input) {
                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber.number = input;
                        return phoneNumber;
                    }
                })
                .toSet();
    }

    public static Function<CarpoolManagement.CarInfo, Car> transformDriverCars(final Long driver) {
        return new Function<CarpoolManagement.CarInfo, Car>() {
            @Override
            public Car apply( CarpoolManagement.CarInfo input) {
                return new Car(input.brand, input.description, input.model, new Driver(driver));
            }
        };
    }
}
