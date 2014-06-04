package com.taxiservice.model;


import com.taxiservice.model.reader.DriverReader;
import com.taxiservice.model.writer.DriverManagement;

import java.util.List;

public interface Searcher {

    List<DriverDetails> drivers(String query);

    public static class DriverDetails {
        final public long id;
        final public String name;
        final public List<String> phones;
        final public long rate;
        final public List<DriverReader.CityLine> cities;
        final public List<DriverManagement.DriverPrice> prices;
        final public String site;
        final public String description;

        public DriverDetails(long id, String name, List<DriverManagement.DriverPrice> driveTypes, List<DriverReader.CityLine> cities, List<String> phones, long rate, String site, String description) {
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

}
