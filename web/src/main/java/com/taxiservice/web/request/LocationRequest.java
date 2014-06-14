package com.taxiservice.web.request;

public class LocationRequest {

    private float lat;
    private float lng;
    private long radius;

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public long getRadius() {
        return radius;
    }
}
