package com.taxiservice.model.reader;

import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * @author Herman Zamula
 */
@Transactional(readOnly = true)
public interface DetailsReader<ID> {

    DriverItem readDriver(@NotNull ID actor, @NotNull ID driver);

    PassengerItem readPassenger(@NotNull ID actor, @NotNull ID passenger);

    TripItem readTrip(@NotNull ID actor, @NotNull ID trip);

    class DriverItem<ID> {
    }

    class PassengerItem<ID> {
    }

    class TripItem<ID> {
    }
}
