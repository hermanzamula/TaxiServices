package com.taxiservice.web.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taxiservice.model.writer.CarpoolManagement;

/**
 * @author Herman Zamula
 */
public class CarInfoMixin {

    @SuppressWarnings("unused")
    public CarInfoMixin(
            @JsonProperty("model")String model,
            @JsonProperty("brand")String brand,
            @JsonProperty("description")String description) {
    }
}
