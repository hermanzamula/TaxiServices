package com.taxiservice.model.internal.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@Table(name = "phone_number")
@Entity
public class PhoneNumber extends AbstractPersistable<Long> {

    private String number;

    @Column(name = "number", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneNumber(Long id) {
        setId(id);
    }

    public PhoneNumber() {
    }

    public PhoneNumber(String number, TaxiDriver taxiDriver) {
        this.number = number;
        this.taxiDriver = taxiDriver;
    }

    @ManyToOne
    @JoinColumn(name = "taxi_driver_id", referencedColumnName = "id", nullable = false)
    private TaxiDriver taxiDriver;

    public TaxiDriver getTaxiDriver() {
        return taxiDriver;
    }

    public void setTaxiDriver(TaxiDriver taxiDriverByTaxiDriverId) {
        this.taxiDriver = taxiDriverByTaxiDriverId;
    }
}
