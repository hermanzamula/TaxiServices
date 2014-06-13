package com.taxiservice.model.util;


import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.taxiservice.model.Location;
import com.taxiservice.model.entity.*;
import com.taxiservice.model.reader.CarpoolReader;
import com.taxiservice.model.reader.NotificationReader;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.google.common.collect.FluentIterable.from;

@Component
public class Transformers {

    public static final Function<PhoneNumber, String> PHONE_NUMBER_TRANSFORMER = new Function<PhoneNumber, String>() {
        @Override
        public String apply(PhoneNumber input) {
            return input.number;
        }
    };
    public static final Function<Passenger, CarpoolReader.PassengerLine> PASSENGER_TRANSFORMER = new Function<Passenger, CarpoolReader.PassengerLine>() {
        @Override
        public CarpoolReader.PassengerLine apply(Passenger input) {
            return new CarpoolReader.PassengerLine<>(input.getId(),
                    input.getUser().getFullName(),
                    input.getDescription(),
                    new Location(input.getCurrentLocation().lng, input.getCurrentLocation().lat),
                    input.getRate(),
                    from(input.getPhoneNumbers()).transform(PHONE_NUMBER_TRANSFORMER).toSet()
            );
        }
    };
    public static final Function<Driver, CarpoolReader.DriverLine> DRIVER_LINE_TRANSFORMER = new Function<Driver, CarpoolReader.DriverLine>() {
        @Override
        public CarpoolReader.DriverLine apply(Driver input) {
            return new CarpoolReader.DriverLine<>(input.getId(),
                    input.getUser().getFullName(),
                    input.getRate(),
                    from(input.getPhoneNumbers()).transform(PHONE_NUMBER_TRANSFORMER).toList());
        }
    };
    public static final Function<Trip, CarpoolReader.TripLine> TRIP_LINE_TRANSFORMER = new Function<Trip, CarpoolReader.TripLine>() {
        @Override
        public CarpoolReader.TripLine apply(Trip input) {
            return new CarpoolReader.TripLine<Long>(input.getId(), input.getName(), input.getDescription(), input.getPassengersLimit(), Iterables.size(input.getSubscribedPassengers()),
                    new Location(input.getStart().lng, input.getStart().lat), new Location(input.getEnd().lng, input.getEnd().lat));
        }
    };
    public static final Function<Message, NotificationReader.NotificationLine> NOTIFICATION_TRANSFORMER = new Function<Message, NotificationReader.NotificationLine>() {
        @Override
        public NotificationReader.NotificationLine apply(Message input) {
            return new NotificationReader.NotificationLine<>(input.getId(), input.getHeading(), input.getBody(), input.getSender().getFullName(), input.getRecipient().getFullName(), input.getDate());
        }
    };
    public static final Function<Comment, CarpoolReader.Feedback> COMMENTS_TRANSFORMER = new Function<Comment, CarpoolReader.Feedback>() {
        @Override
        public CarpoolReader.Feedback apply(Comment input) {
            return new CarpoolReader.Feedback<>(input.getUser().getFullName(), input.getDriver().getId(), input.getMessage(), input.getDate());
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
}
