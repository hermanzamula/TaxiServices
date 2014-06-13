package com.taxiservice.model;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PredefinedDataCreator {

    long createAdmin(String name, String lastName, String email, String password);

    long createUser(String name, String lastName, String email, String password);

    long createDriver(String name, String description, String site, long city, List<String> numbers);
}
