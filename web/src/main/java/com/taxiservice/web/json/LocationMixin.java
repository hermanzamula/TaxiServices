package com.taxiservice.web.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Herman Zamula
 */
public class LocationMixin {

    @SuppressWarnings("unused")
    public LocationMixin(
            @JsonProperty("lng")float lng,
            @JsonProperty("lat") float lat
    ) {
    }
}
