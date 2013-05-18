package com.taxiservice.model.writer;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DriverManagement {

    long createDriver(DriverInfo driverInfo);

    long removeDriver(long user, long driver);

    public static class DriverInfo {

        final public long id;
        final public String name;
        final public List<String> phones;
        final public long city;
        final List<DriveType> driveTypes;

        public DriverInfo(long id, String name, List<String> phones, long city, List<DriveType> driveTypes) {
            this.id = id;
            this.name = name;
            this.phones = phones;
            this.city = city;
            this.driveTypes = driveTypes;
        }
    }

    public static class DriveType {
        public final long id;
        public final String name;
        public final double price;

        public DriveType(long id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }
}
