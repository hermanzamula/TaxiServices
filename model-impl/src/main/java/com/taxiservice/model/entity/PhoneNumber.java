package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@Table(name = "phone_number", schema = "", catalog = "taxiservice")
@Entity
public class PhoneNumber extends AbstractPersistable<Integer> {


    private String number;

    @Column(name = "number", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    private TaxiDriver taxiDriver;

    @ManyToOne
    @JoinColumn(name = "taxi_driver_id", referencedColumnName = "id", nullable = false)
    public TaxiDriver getTaxiDriver() {
        return taxiDriver;
    }

    public void setTaxiDriver(TaxiDriver taxiDriverByTaxiDriverId) {
        this.taxiDriver = taxiDriverByTaxiDriverId;
    }
}
