package com.taxiservice.model.internal.util;


import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.taxiservice.model.AuthenticationService;
import com.taxiservice.model.internal.entity.*;
import com.taxiservice.model.reader.DriverReader;
import com.taxiservice.model.writer.DriverManagement;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;

@Component
public class Transformers {

    public static final Function<PhoneNumber, String> PHONE_NUMBER_TRANSFORMER = new Function<PhoneNumber, String>() {
        @Override
        public String apply(PhoneNumber input) {
            return input.getNumber();
        }
    };
    public static final Function<City, Long> CITY_TO_ID = new Function<City, Long>() {
        @Override
        public Long apply(City input) {
            return input.getId();
        }
    };
    public static final Function<TaxiDriver, DriverManagement.DriverDetails> TO_DRIVER_DETAILS = new Function<TaxiDriver, DriverManagement.DriverDetails>() {
        @Override
        public DriverManagement.DriverDetails apply(TaxiDriver input) {
            return new DriverManagement.DriverDetails(input.getId(), input.getName(), from(input.getPrices()).transform(TO_DRIVE_TYPE).toList(),
                    from(input.getCities()).transform(CITY_TO_ID).toList(),
                    from(input.getPhoneNumbers()).transform(PHONE_NUMBER_TRANSFORMER).toList(),
                    input.getRate(), input.getSite(), input.getDescription());
        }
    };
    public static final Function<Price, DriverManagement.DriverPrice> TO_DRIVE_TYPE = new Function<Price, DriverManagement.DriverPrice>() {
        @Override
        public DriverManagement.DriverPrice apply(Price input) {
            final DriveType type = input.getDriveType();
            return new DriverManagement.DriverPrice(type.getId(), type.getName(), input.getInfo().getMinimum().doubleValue(), input.getInfo().getMaximum().doubleValue());
        }
    };
    public static final Function<TaxiDriver, DriverReader.DriverLine> TO_DRIVE_LINE = new Function<TaxiDriver, DriverReader.DriverLine>() {
        @Override
        public DriverReader.DriverLine apply(TaxiDriver input) {
            return new DriverReader.DriverLine(input.getId(),
                    input.getName(), input.getRate(),
                    from(input.getPhoneNumbers())
                            .transform(PHONE_NUMBER_TRANSFORMER)
                            .toList());
        }
    };
    public static final Function<Comment, DriverReader.Feedback> TO_FEEDBACK = new Function<Comment, DriverReader.Feedback>() {
        @Override
        public DriverReader.Feedback apply(Comment input) {
            return new DriverReader.Feedback(input.getUser(),
                    input.getDriver().getId(), input.getMessage(),
                    input.getDate());
        }
    };

    public static final Function<City,DriverReader.CityLine> TO_CITY_LINE = new Function<City, DriverReader.CityLine>() {
        @Override
        public DriverReader.CityLine apply(City input) {
            return new DriverReader.CityLine(input.getName(), input.getId(), input.getTaxiDrivers().size());
        }
    };
    public static final Function<AccessToken,AuthenticationService.AccessTokenInfo> TO_TOKEN_INFO = new Function<AccessToken, AuthenticationService.AccessTokenInfo>() {
        @Override
        public AuthenticationService.AccessTokenInfo apply(AccessToken input) {

            final AuthenticationService.AccessTokenInfo tokenInfo = new AuthenticationService.AccessTokenInfo();
            tokenInfo.value = input.getToken();

            return tokenInfo;
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
