package com.taxiservice.web.response;

/**
 * @author Herman Zamula
 */
public class ValueResponse<T> {

    private T value;

    public ValueResponse(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
