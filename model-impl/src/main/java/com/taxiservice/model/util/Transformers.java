package com.taxiservice.model.util;


import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.taxiservice.model.entity.DriveType;
import com.taxiservice.model.entity.PhoneNumber;
import com.taxiservice.model.entity.Price;
import com.taxiservice.model.entity.TaxiDriver;
import com.taxiservice.model.reader.DriverReader;
import com.taxiservice.model.writer.DriverManagement;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;

public class Transformers {

    public static final Function<PhoneNumber, String> PHONE_NUMBER_TRANSFORMER = new Function<PhoneNumber, String>() {
        @Override
        public String apply(PhoneNumber input) {
            return input.getNumber();
        }
    };

    public static final Function<TaxiDriver, DriverManagement.DriverDetails> DRIVER_INFO_TRANSFORMER = new Function<TaxiDriver, DriverManagement.DriverDetails>() {
        @Override
        public DriverManagement.DriverDetails apply(TaxiDriver input) {
            return new DriverManagement.DriverDetails(input.getId(), input.getName(), from(input.getPrices()).transform(TO_DRIVE_TYPE).toImmutableList(), input.getCity().getId(),
                    from(input.getPhoneNumbers()).transform(PHONE_NUMBER_TRANSFORMER).toImmutableList(),
                    input.getRate(), input.getSite(), input.getDescription());
        }
    };

    public static final Function<Price, DriverManagement.Type> TO_DRIVE_TYPE = new Function<Price, DriverManagement.Type>() {
        @Override
        public DriverManagement.Type apply(Price input) {
            final DriveType type = input.getDriveType();
            return new DriverManagement.Type(type.getId(), type.getName(), (input.getInfo().getMaximum() + input.getInfo().getMaximum())/2);
        }
    };
    public static final Function<TaxiDriver, DriverReader.DriverLine> DRIVER_LINE_TRANSFORMER = new Function<TaxiDriver, DriverReader.DriverLine>() {
        @Override
        public DriverReader.DriverLine apply(TaxiDriver input) {
            return new DriverReader.DriverLine(input.getId(),
                    input.getName(), input.getRate(),
                    from(input.getPhoneNumbers())
                            .transform(PHONE_NUMBER_TRANSFORMER)
                            .toImmutableList());
        }
    };

    public static FluentIterable<PhoneNumber> phoneNumbersFromStrings(final TaxiDriver driver, List<String> phones) {
        return from(phones).transform(new Function<String, PhoneNumber>() {
            @Override
            public PhoneNumber apply(String input) {
                return new PhoneNumber(input, driver);
            }
        });
    }
}
