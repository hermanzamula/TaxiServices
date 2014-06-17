package com.taxiservice.web.demo;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.taxiservice.model.Location;
import com.taxiservice.model.PredefinedDataCreator;
import com.taxiservice.model.reader.CarpoolReader;
import com.taxiservice.model.reader.DetailsReader;
import com.taxiservice.model.writer.CarpoolManagement;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;

import static com.google.common.collect.ImmutableSet.of;

/**
 * @author Herman Zamula
 */
@Component
public class DemoDataCreator {

    private final CarpoolManagement.DriverInfo driverInfo = new CarpoolManagement.DriverInfo(Collections.singleton("+380559988333"), "Опыт вождения 3 года.", of(new CarpoolManagement.CarInfo("A8", "Audi", "2010 года")));
    @Inject
    private PredefinedDataCreator predefinedDataCreator;

    @Inject
    private CarpoolManagement<Long> carpoolManagement;
    @Inject
    private CarpoolReader<Long> reader;
    @Inject
    private DetailsReader<Long> detailsReader;

    @PostConstruct
    public void create(){
        final long user = predefinedDataCreator.createUser("Сергей", "Сергиенко", "sergiy.sergienko@example.com", "pwd");
        final Long driver = carpoolManagement.createDriver(user, driverInfo);
        final DetailsReader.DriverItem<Long> driverItem = detailsReader.readDriver(user, driver);
        final Long trip = carpoolManagement.createTrip(driver, driverItem.cars.iterator().next().id, new CarpoolManagement.TripInfo(
                "Поездка в Киев",
                "Еду в киев в конце инюня! Хочу разделить поездку с люьми по интересам :)",
                new Location(36.5221921f, 50.0045805f),
                new Location(30.2348948f, 50.447914f),
                new Date(new Date().getTime() + 24 * 60 * 60 * 1000),
                3
        ));
    }
}
