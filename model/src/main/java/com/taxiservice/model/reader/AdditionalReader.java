package com.taxiservice.model.reader;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AdditionalReader {

    List<DriverReader.CityLine> readCities(long country);

}
