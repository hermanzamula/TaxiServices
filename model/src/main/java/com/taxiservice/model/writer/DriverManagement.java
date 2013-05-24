package com.taxiservice.model.writer;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DriverManagement {

    long createDriver(DriverDetails driverDetails);

    void removeDriver(long user, long driver);

    void likeDriver(long user, long driver);

    void dislikeDriver(long user, long driver);

    void updateDriverInfo(long driver, DriverDetails driverDetails);

    public static class DriverDetails {

        final public long id;
        final public String name;
        final public List<String> phones;
        final public long rate;
        final public long city;
        final public List<Type> driveTypes;
        final public String site;
        final public String description;

        public DriverDetails(long id, String name, List<Type> driveTypes, long city, List<String> phones, long rate, String site, String description) {
            this.id = id;
            this.name = name;
            this.phones = phones;
            this.city = city;
            this.driveTypes = driveTypes;
            this.rate = rate;
            this.site = site;
            this.description = description;
        }
    }

    public static class Type {
        public final long id;
        public final String name;
        public final double price;

        public Type(long id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }
}
