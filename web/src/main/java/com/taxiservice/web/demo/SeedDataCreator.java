package com.taxiservice.web.demo;


import com.taxiservice.model.PredefinedDataCreator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class SeedDataCreator {

    private static final Logger LOGGER = Logger.getLogger(SeedDataCreator.class);

    @Inject
    PredefinedDataCreator initiator;


    @PostConstruct
    public void create(){
        LOGGER.log(Level.INFO, "Seed data creator init");

    }
}
