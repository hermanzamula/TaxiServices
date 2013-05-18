package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;


@Table(name = "drive_type", schema = "", catalog = "taxiservice")
@Entity
public class DriveType extends AbstractPersistable<Long> {


    private String name;

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description;

    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 256, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private BigInteger price;

    @Column(name = "price", nullable = true, insertable = true, updatable = true, length = 2, precision = 0)
    @Basic
    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    private Collection<TaxiDriver> taxiDrivers;

    @OneToMany(mappedBy = "driveType")
    public Collection<TaxiDriver> getTaxiDrivers() {
        return taxiDrivers;
    }

    public void setTaxiDrivers(Collection<TaxiDriver> taxiDriversById) {
        this.taxiDrivers = taxiDriversById;
    }

    private Collection<UserBonus> userBonuses;

    @OneToMany(mappedBy = "driveType")
    public Collection<UserBonus> getUserBonuses() {
        return userBonuses;
    }

    public void setUserBonuses(Collection<UserBonus> userBonusesById) {
        this.userBonuses = userBonusesById;
    }
}
