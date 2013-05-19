package com.taxiservice.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "taxi_driver_has_drive_type", schema = "", catalog = "taxiservice")
@Entity
public class TaxiDriverHasDriveType implements Serializable{

    private TaxiDriver taxiDriver;
    private DriveType driveType;

    @Id
    @ManyToOne
    @JoinColumn(name = "taxi_driver_id", referencedColumnName = "id", nullable = false)
    public TaxiDriver getTaxiDriver() {
        return taxiDriver;
    }

    public void setTaxiDriver(TaxiDriver taxiDriverByTaxiDriverId) {
        this.taxiDriver = taxiDriverByTaxiDriverId;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "drive_type_id", referencedColumnName = "id", nullable = false)
    public DriveType getDriveType() {
        return driveType;
    }

    public void setDriveType(DriveType driveTypeByDriveTypeId) {
        this.driveType = driveTypeByDriveTypeId;
    }
}
