package com.taxicervice.web.demo;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SeedDataCreator {

    private static final Logger LOGGER = Logger.getLogger(SeedDataCreator.class);

    @PostConstruct
    public void create(){
        LOGGER.log(Level.INFO, "Seed data creator init");
    }
}
