package com.taxiservice.model;


import java.util.List;

public interface PredefinedDataCreator {

    long createAdmin(String name, String lastName, String email, String password);

    long createUser(String name, String lastName, String email, String password);

    long createDriver(String name, String description, String site, long city, List<String> numbers, List<HasDriveType> driveTypes);

    long createCity(String name, long country);

    long createCountry(String name);

    long driveType(String name, String description);

}
