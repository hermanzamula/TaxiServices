package com.taxiservice.model.util;


import com.google.common.base.Function;
import com.taxiservice.model.entity.DriveType;
import com.taxiservice.model.entity.PhoneNumber;
import com.taxiservice.model.entity.TaxiDriver;
import com.taxiservice.model.entity.TaxiDriverHasDriveType;
import com.taxiservice.model.reader.DriverReader;
import com.taxiservice.model.writer.DriverManagement;

import static com.google.common.collect.FluentIterable.from;

public class Transformers {

    public static final Function<PhoneNumber, String> PHONE_NUMBER_TRANSFORMER = new Function<PhoneNumber, String>() {
        @Override
        public String apply(PhoneNumber input) {
            return input.getNumber();
        }
    };


    public static final Function<TaxiDriver, DriverManagement.DriverInfo> DRIVER_INFO_TRANSFORMER = new Function<TaxiDriver, DriverManagement.DriverInfo>() {
        @Override
        public DriverManagement.DriverInfo apply(TaxiDriver input) {
            return new DriverManagement.DriverInfo(input.getId(), input.getName(), input.getCity().getId(),
                    from(input.getPhoneNumbers()).transform(PHONE_NUMBER_TRANSFORMER).toImmutableList(),
                    from(input.getDriveTypes()).transform(DRIVE_TYPE_TRANSFORMER).toImmutableList());
        }
    };


    public static final Function<DriveType,DriverManagement.DriveType> DRIVE_TYPE_TRANSFORMER = new Function<DriveType, DriverManagement.DriveType>() {
        @Override
        public DriverManagement.DriveType apply(DriveType input) {
            return new DriverManagement.DriveType(input.getId(), input.getName(), input.getPrice().doubleValue());
        }
    };


    public static final Function<? super TaxiDriver,DriverReader.DriverLine> DRIVER_LINE_TRANSFORMER = new Function<TaxiDriver, DriverReader.DriverLine>() {
        @Override
        public DriverReader.DriverLine apply(TaxiDriver input) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };
}
