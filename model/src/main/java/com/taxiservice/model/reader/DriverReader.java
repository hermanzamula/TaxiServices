package com.taxiservice.model.reader;

import com.taxiservice.model.writer.DriverManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface DriverReader {

    List<DriverManagement.DriverDetails> readDriversByCity(long city);

    List<DriverManagement.DriverDetails> readDriversByDriveType(long driveType, long city);

    List<DriverManagement.DriverDetails> readAll();

    List<DriverManagement.DriverDetails> readFavourites(long userId);

    List<DriverManagement.DriverDetails> readCurrentCityFavourites(long userId);

    List<DriverManagement.DriverDetails> readCityFavourites(long actor, long cityId);

    List<Feedback> readComments(long driver);

    public class DriverLine {
        public final long id;
        public final String name;
        public final long rate;
        public final List<String> phones;

        public DriverLine(long id, String name, long rate, List<String> phones) {
            this.id = id;
            this.name = name;
            this.rate = rate;
            this.phones = phones;
        }
    }

    public class Feedback {
        public final String username;
        public final long driver;
        public final String message;
        public final Date date;

        public Feedback(String username, long driver, String message, Date date) {
            this.username = username;
            this.driver = driver;
            this.message = message;
            this.date = date;
        }
    }

    public class CityLine {
    }
}
