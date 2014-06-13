package com.taxiservice.model.util;


import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.taxiservice.model.entity.Comment;
import com.taxiservice.model.entity.Driver;
import com.taxiservice.model.entity.PhoneNumber;
import com.taxiservice.model.reader.CarpoolReader;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;

@Component
public class Transformers {

    public static final Function<PhoneNumber, String> PHONE_NUMBER_TRANSFORMER = new Function<PhoneNumber, String>() {
        @Override
        public String apply(PhoneNumber input) {
            return input.number;
        }
    };
    public static final Function<Driver, CarpoolReader.DriverLine> TO_DRIVE_LINE = new Function<Driver, CarpoolReader.DriverLine>() {
        @Override
        public CarpoolReader.DriverLine apply(Driver input) {
            return/* new CarpoolReader.DriverLine(input.getId(),
                    input.getName(), input.getRate(),
                    from(input.getPhoneNumbers())
                            .transform(PHONE_NUMBER_TRANSFORMER)
                            .toList());*/null;
        }
    };
    public static final Function<Comment, CarpoolReader.Feedback> TO_FEEDBACK = new Function<Comment, CarpoolReader.Feedback>() {
        @Override
        public CarpoolReader.Feedback apply(Comment input) {
            return new CarpoolReader.Feedback(input.getUser().getEmail(),
                    input.getDriver().getId(), input.getMessage(),
                    input.getDate());
        }
    };

    public static FluentIterable<PhoneNumber> phoneNumbersFromStrings(final Driver driver, List<String> phones) {
        return from(phones).transform(new Function<String, PhoneNumber>() {
            @Override
            public PhoneNumber apply(String input) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.number = input;
                return phoneNumber;
            }
        });
    }
}
