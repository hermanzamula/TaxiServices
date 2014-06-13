package com.taxiservice.web.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * @author Herman Zamula
 */
public class PassengerInfoMixin {
    @SuppressWarnings("unused")
    public PassengerInfoMixin(
            @JsonProperty("phones") Set<String> phones) {
    }
}
