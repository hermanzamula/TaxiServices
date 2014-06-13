package com.taxiservice.model;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface Searcher {

    List<DriverDetails> drivers(String query);

    public static class DriverDetails {
        final public long id;
        final public String name;
        final public List<String> phones;
        final public long rate;
        final public String site;
        final public String description;

        public DriverDetails(long id, String name, List<String> phones, long rate, String site, String description) {
            this.id = id;
            this.name = name;
            this.phones = phones;
            this.rate = rate;
            this.site = site;
            this.description = description;
        }
    }

}
