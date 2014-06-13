package com.taxiservice.web.request;

/**
 * @author Herman Zamula
 */
public class RangeLocationRequest {

    private float startLat;
    private float startLng;

    private float endLat;
    private float endLng;

    private long radius;

    public float getStartLat() {
        return startLat;
    }

    public void setStartLat(float startLat) {
        this.startLat = startLat;
    }

    public float getStartLng() {
        return startLng;
    }

    public void setStartLng(float startLng) {
        this.startLng = startLng;
    }

    public float getEndLat() {
        return endLat;
    }

    public void setEndLat(float endLat) {
        this.endLat = endLat;
    }

    public float getEndLng() {
        return endLng;
    }

    public void setEndLng(float endLng) {
        this.endLng = endLng;
    }

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }
}
