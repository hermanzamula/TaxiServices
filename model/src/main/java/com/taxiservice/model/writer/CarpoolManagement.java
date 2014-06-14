package com.taxiservice.model.writer;

import com.taxiservice.model.Location;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @author Herman Zamula
 */
@Transactional
public interface CarpoolManagement<ID> {

    void subscribe(@NotNull ID actor, @NotNull ID trip);

    void removeSubscriber(@NotNull ID actor, ID trip, @NotNull ID passenger);

    void rateDriver(@NotNull ID actor, @NotNull ID driver, int rate);

    void leaveComment(@NotNull ID actor, @NotNull ID driver, String message);

    void ratePassenger(@NotNull ID actor, @NotNull ID passenger, int rate);

    ID createTrip(@NotNull ID actor, @NotNull ID car, TripInfo tripInfo);

    ID createDriver(@NotNull ID actor, DriverInfo driverInfo);

    ID createPassenger(@NotNull ID actor, PassengerInfo passengerInfo);

    class TripInfo {

        public final String name;
        public final String description;
        public final Location start;
        public final Location end;
        public final Date startDate;
        public final int passengersLimit;

        public TripInfo(String name, String description, Location start, Location end, Date startDate, int passengersLimit) {
            this.name = name;
            this.description = description;
            this.start = start;
            this.end = end;
            this.startDate = startDate;
            this.passengersLimit = passengersLimit;
        }
    }


    class DriverInfo {

        final public Set<String> phones;
        final public String description;
        final public Set<CarInfo> cars;

        public DriverInfo(Set<String> phones, String description, Set<CarInfo> cars) {
            this.phones = phones;
            this.description = description;
            this.cars = cars;
        }
    }

    class CarInfo {

        final public String model;
        final public String brand;
        final public String description;

        public CarInfo(String model, String brand, String description) {
            this.model = model;
            this.brand = brand;
            this.description = description;
        }
    }

    class PassengerInfo {

        final public Set<String> phones;

        public PassengerInfo(Set<String> phones) {
            this.phones = phones;
        }
    }
}
