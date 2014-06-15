package com.taxiservice.model.reader;

import com.google.common.collect.ImmutableSet;
import com.taxiservice.model.Location;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
public interface CarpoolReader<ID> {

    Set<DriverLine> readDriversNear(@NotNull ID actor, Location location, long searchRadius);

    Set<PassengerLine> readPassengers(@NotNull ID actor, @NotNull ID trip);

    Set<TripLine> readTripsNear(@NotNull ID actor, Location location, long searchRadius);

    Set<TripLine> readTrips(@NotNull ID actor, Location from, Location to, long searchRadius);

    Set<Feedback> readComments(@NotNull ID driver);

    public class DriverLine<ID> {

        public final ID id;
        public final String name;
        public final long rate;
        public final List<String> phones;

        public DriverLine(ID id, String name, long rate, List<String> phones) {
            this.id = id;
            this.name = name;
            this.rate = rate;
            this.phones = phones;
        }
    }

    public class Feedback<ID> {

        public final String username;
        public final ID driver;
        public final String message;
        public final Date date;

        public Feedback(String username, ID driver, String message, Date date) {
            this.username = username;
            this.driver = driver;
            this.message = message;
            this.date = date;
        }
    }

    class PassengerLine<ID> {

        public final ID id;
        public final String name;
        public final String description;
        public final Location location;
        public final long rate;
        public final ImmutableSet<String> phones;

        public PassengerLine(ID id, String fullName, String description, Location location, long rate, ImmutableSet<String> phones) {
            this.id = id;
            this.name = fullName;
            this.description = description;
            this.location = location;
            this.rate = rate;
            this.phones = phones;
        }
    }

    class TripLine <ID> {

        public final ID id;
        public final String name;
        public final String description;
        public final int passengersLimit;
        public final int free;
        public final Location start;
        public final Location end;
        public final Date startDate;

        public TripLine(ID id, String name, String description, int passengersLimit, int free, Location start, Location end, Date startDate) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.passengersLimit = passengersLimit;
            this.free = free;
            this.start = start;
            this.end = end;
            this.startDate = startDate;
        }
    }
}
