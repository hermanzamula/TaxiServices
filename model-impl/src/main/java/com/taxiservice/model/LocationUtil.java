package com.taxiservice.model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * @author Herman Zamula
 */
public final class LocationUtil {

    private final static double RAD = Math.PI / 180.0;
    public static final double EARTH_RADIUS = 6371.01;

    public static double distanceMeters(Location one, Location second) {

        double phi1 = one.lat * RAD;
        double phi2 = second.lat * RAD;
        double lam1 = one.lng * RAD;
        double lam2 = second.lng * RAD;

        return EARTH_RADIUS * 1000 * Math.acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
    }
}
