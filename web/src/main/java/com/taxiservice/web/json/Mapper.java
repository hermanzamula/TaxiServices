package com.taxiservice.web.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxiservice.model.Location;
import com.taxiservice.model.writer.CarpoolManagement;

/**
 * @author Herman Zamula
 */
public class Mapper extends ObjectMapper {

    public Mapper() {
        super();
        addMixInAnnotations(Location.class, LocationMixin.class);
        addMixInAnnotations(CarpoolManagement.DriverInfo.class, DriverInfoMixin.class);
        addMixInAnnotations(CarpoolManagement.PassengerInfo.class, PassengerInfoMixin.class);
        addMixInAnnotations(CarpoolManagement.TripInfo.class, TripInfoMixin.class);
        addMixInAnnotations(CarpoolManagement.CarInfo.class, CarInfoMixin.class);
    }
}
