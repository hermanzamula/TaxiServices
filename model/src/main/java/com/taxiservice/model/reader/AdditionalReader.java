package com.taxiservice.model.reader;


import java.util.List;

public interface AdditionalReader {

    List<DriverReader.CityLine> readCities(long country);

    List<DriverReader.Feedback> readLastComments(int size);

    List<DriverReader.CountryLine> readCountries();

    DriverReader.CityLine readCity(long id);
}
