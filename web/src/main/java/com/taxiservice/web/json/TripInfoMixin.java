package com.taxiservice.web.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taxiservice.model.Location;

import java.util.Date;

/**
 * @author Herman Zamula
 */
public class TripInfoMixin {

    @SuppressWarnings("unused")
    public TripInfoMixin(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("start") Location start,
            @JsonProperty("end") Location end,
            @JsonProperty("startDate") Date startDate,
            @JsonProperty("passengersLimit") int passengersLimit) {

    }
}
