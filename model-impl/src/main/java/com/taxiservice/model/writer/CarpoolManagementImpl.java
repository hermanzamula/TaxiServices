package com.taxiservice.model.writer;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author Herman Zamula
 */
@Service
public class CarpoolManagementImpl implements CarpoolManagement<Long> {

    @Override
    public void subscribe(@NotNull Long actor, @NotNull Long trip) {

    }

    @Override
    public void removeSubscriber(@NotNull Long actor, @NotNull Long passenger) {

    }

    @Override
    public void rateDriver(@NotNull Long actor, @NotNull Long driver, int rate) {

    }

    @Override
    public void leaveComment(@NotNull Long actor, @NotNull Long driver, String message) {

    }

    @Override
    public void ratePassenger(@NotNull Long actor, @NotNull Long passenger, int rate) {

    }

    @Override
    public Long createTrip(@NotNull Long actor, @NotNull Long car, TripInfo tripInfo) {
        return null;
    }

    @Override
    public Long createDriver(@NotNull Long actor, DriverInfo driverInfo) {
        return null;
    }

    @Override
    public Long createPassenger(@NotNull Long actor, PassengerInfo passengerInfo) {
        return null;
    }
}
