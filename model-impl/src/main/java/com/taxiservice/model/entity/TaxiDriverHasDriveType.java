package com.taxiservice.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "taxi_driver_has_drive_type", schema = "", catalog = "taxiservice")
@Entity
public class TaxiDriverHasDriveType implements Serializable {


    @Id
    @ManyToOne
    @JoinColumn(name = "taxi_driver_id", referencedColumnName = "id", nullable = false)
    private TaxiDriver taxiDriver;

    @Id
    @ManyToOne
    @JoinColumn(name = "drive_type_id", referencedColumnName = "id", nullable = false)
    private DriveType driveType;
    private Price price;

    public TaxiDriverHasDriveType(TaxiDriver taxiDriver, DriveType driveType, Price price) {
        this.taxiDriver = taxiDriver;
        this.driveType = driveType;
        this.price = price;
    }

    public TaxiDriverHasDriveType() {
    }

    public TaxiDriver getTaxiDriver() {
        return taxiDriver;
    }

    public void setTaxiDriver(TaxiDriver taxiDriverByTaxiDriverId) {
        this.taxiDriver = taxiDriverByTaxiDriverId;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public void setDriveType(DriveType driveTypeByDriveTypeId) {
        this.driveType = driveTypeByDriveTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "Price_id", referencedColumnName = "id", nullable = false)
    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
