package com.taxiservice.model.reader;

import com.taxiservice.model.writer.DriverManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface DriverReader {

    List<DriverManagement.DriverInfo> readDriversByCity(long city, long region, long country);

    List<DriverManagement.DriverInfo> readDriversByDriveType(long driveType, long city, long region, long country);

    List<DriverManagement.DriverInfo> readAll();

    List<DriverManagement.DriverInfo> readFavourites(long userId);

    List<DriverManagement.DriverInfo> readCityFavourites(long userId);
}
