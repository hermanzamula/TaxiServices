package com.taxiservice.model;


import com.taxiservice.model.writer.CarpoolManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface PredefinedDataCreator {

    long createAdmin(String name, String lastName, String email, String password);

    long createUser(String name, String lastName, String email, String password);

    long createDriver(long user, String description, List<String> numbers, Set<CarpoolManagement.CarInfo> cars);
}
