package com.taxiservice.model.reader;

import com.google.common.collect.ImmutableSet;
import com.taxiservice.model.Location;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Herman Zamula
 */
@Transactional(readOnly = true)
public interface DetailsReader<ID> {

    DriverItem<ID> readDriver(@NotNull ID actor, @NotNull ID driver);

    PassengerItem<ID> readPassenger(@NotNull ID actor, @NotNull ID passenger);

    TripItem<ID> readTrip(@NotNull ID actor, @NotNull ID trip);

    class DriverItem<ID> {

        public final ID id;
        public final ImmutableSet<String> phones;
        public final String name;
        public final String email;
        public final String description;
        public final Location location;
        public final long rate;
        public final ImmutableSet<CarpoolReader.Feedback<ID>> comments;
        public final ImmutableSet<CarpoolReader.TripLine<ID>> trips;
        public final ImmutableSet<CarInfo<ID>> cars;

        public DriverItem(ID id, ImmutableSet<String> phones, String name, String email, String description, Location location, long rate, ImmutableSet<CarpoolReader.Feedback<ID>> comments, ImmutableSet<CarpoolReader.TripLine<ID>> trips, ImmutableSet<CarInfo<ID>> transform) {
            this.id = id;
            this.phones = phones;
            this.name = name;
            this.email = email;
            this.description = description;
            this.location = location;
            this.rate = rate;
            this.comments = comments;
            this.trips = trips;
            this.cars = transform;
        }
    }

    class PassengerItem<ID> {

        public final ID id;
        public final String name;
        public final ImmutableSet<String> phones;
        public final String email;
        public final long rate;
        public final String description;
        public final Location location;
        public final ImmutableSet<CarpoolReader.TripLine<ID>> trips;

        public PassengerItem(ID id, String name, ImmutableSet<String> phones, String email, long rate, String description, Location location, ImmutableSet<CarpoolReader.TripLine<ID>> trips) {

            this.id = id;
            this.name = name;
            this.phones = phones;
            this.email = email;
            this.rate = rate;
            this.description = description;
            this.location = location;
            this.trips = trips;
        }
    }

    class TripItem<ID> {

        public final ID id;
        public final String description;
        public final String name;
        public final CarpoolReader.DriverLine<ID> driverLine;
        public final CarInfo<ID> carInfo;
        public final ImmutableSet<CarpoolReader.PassengerLine<ID>> passengerLines;
        public final Location start;
        public final Location end;
        public final Date startDate;

        public TripItem(ID id, String description, String name, CarpoolReader.DriverLine<ID> driverLine, CarInfo<ID> carInfo, ImmutableSet<CarpoolReader.PassengerLine<ID>> passengerLines, Location start, Location end, Date startDate) {

            this.id = id;
            this.description = description;
            this.name = name;
            this.driverLine = driverLine;
            this.carInfo = carInfo;
            this.passengerLines = passengerLines;
            this.start = start;
            this.end = end;
            this.startDate = startDate;
        }
    }


    class CarInfo<ID> {

        public final ID id;
        public final String model;
        public final String brand;
        public final String description;

        public CarInfo(ID id, String model, String brand, String description) {

            this.id = id;
            this.model = model;
            this.brand = brand;
            this.description = description;
        }
    }
}
