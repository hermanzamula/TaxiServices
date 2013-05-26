package com.taxiservice.model.writer;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DriverManagement {

    long createDriver(DriverInfo driverInfo);

    void removeDriver(long user, long driver);

    void likeDriver(long user, long driver);

    void dislikeDriver(long user, long driver);

    void updateDriverInfo(long driver, DriverInfo driverInfo);

    void comment(long user, long driver, String message);

    public static class DriverDetails {

        final public long id;
        final public String name;
        final public List<String> phones;
        final public long rate;
        final public List<Long> cities;
        final public List<DriverPrice> prices;
        final public String site;
        final public String description;

        public DriverDetails(long id, String name, List<DriverPrice> driveTypes, List<Long> cities, List<String> phones, long rate, String site, String description) {
            this.id = id;
            this.name = name;
            this.phones = phones;
            this.cities = cities;
            this.prices = driveTypes;
            this.rate = rate;
            this.site = site;
            this.description = description;
        }
    }

    public static class DriverPrice {
        public final long id;
        public final String name;
        public final double min;
        public final double max;

        public DriverPrice(long id, String name, double min, double max) {
            this.id = id;
            this.name = name;
            this.min = min;
            this.max = max;
        }
    }

    public static class PriceList {
        public final long driveType;
        public final double min;
        public final double max;
        public final String description;


        public PriceList(long driveType, double min, double max, String description) {
            this.driveType = driveType;
            this.min = min;
            this.max = max;
            this.description = description;
        }
    }

    public static class DriverInfo {
        final public String name;
        final public List<String> phones;
        final public List<Long> cities;
        final public List<PriceList> prices;
        final public String site;
        final public String description;

        public DriverInfo(String name, List<String> phones, List<Long> cities, List<PriceList> prices, String site, String description) {
            this.name = name;
            this.phones = phones;
            this.cities = cities;
            this.prices = prices;
            this.site = site;
            this.description = description;
        }
    }
}
