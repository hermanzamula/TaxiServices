package com.taxiservice.web.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * @author Herman Zamula
 */
public class DriverInfoMixin {

    @SuppressWarnings("unused")
    public DriverInfoMixin(
            @JsonProperty("phones") Set<String> phones,
            @JsonProperty("description") String description
    ) {
    }
}
