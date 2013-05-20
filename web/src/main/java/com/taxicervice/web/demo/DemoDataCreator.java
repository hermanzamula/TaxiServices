package com.taxicervice.web.demo;


import com.taxiservice.model.writer.UserManagement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Component
@DependsOn("seedDataCreator")
public class DemoDataCreator {

    public static final Logger LOGGER = Logger.getLogger(DemoDataCreator.class);

    @Inject
    UserManagement userManagement;

    //TODO: Implement
    @PostConstruct
    public void init(){
        LOGGER.log(Level.INFO, "Demo data creator init");
    }
}
