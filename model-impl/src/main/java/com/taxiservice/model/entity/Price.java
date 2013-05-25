package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Table(name = "price")
@Entity
public class Price extends AbstractPersistable<Long> {


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "taxi_driver_id", referencedColumnName = "id", nullable = false)
    TaxiDriver taxiDriver;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "drive_type_id", referencedColumnName = "id", nullable = false)
    DriveType driveType;
    @Embedded
    PriceInfo info;

     public Price(TaxiDriver taxiDriver, DriveType driveType, PriceInfo info) {
         this.taxiDriver = taxiDriver;
         this.driveType = driveType;
         this.info = info;
     }


    public Price() {
    }

    public PriceInfo getInfo() {
        return info;
    }

    public void setInfo(PriceInfo info) {
        this.info = info;
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

    public PriceInfo getPrice() {
        return info;
    }

    public void setPrice(PriceInfo info) {
        this.info = info;
    }
}
