package com.taxiservice.model;


public class HasDriveType {
    public final long driveType;
    public final double minVal;
    public final double maxVal;
    public final String description;

    public HasDriveType(long driveType, double minVal, double maxVal, String description) {
        this.driveType = driveType;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.description = description;
    }
}
