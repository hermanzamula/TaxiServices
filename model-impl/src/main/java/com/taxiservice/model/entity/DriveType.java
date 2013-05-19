package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;


@Table(name = "drive_type", schema = "", catalog = "taxiservice")
@Entity
public class DriveType extends AbstractPersistable<Long> {


    private String name;
    private String description;
    private BigInteger price;
    @OneToMany(mappedBy = "driveType", cascade = CascadeType.ALL)
    private Collection<UserBonus> userBonuses;
    @ManyToMany
    @JoinTable(name = "taxi_driver_has_drive_type")
    private Collection<TaxiDriver> taxiDrivers;

    public DriveType() {
    }

    public DriveType(Long id) {
        setId(id);
    }

    public DriveType(String name, String description, BigInteger price, Collection<TaxiDriver> taxiDrivers, Collection<UserBonus> userBonuses) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.taxiDrivers = taxiDrivers;
        this.userBonuses = userBonuses;
    }

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 256, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price", nullable = true, insertable = true, updatable = true, length = 2, precision = 0)
    @Basic
    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public Collection<UserBonus> getUserBonuses() {
        return userBonuses;
    }

    public void setUserBonuses(Collection<UserBonus> userBonusesById) {
        this.userBonuses = userBonusesById;
    }

    public Collection<TaxiDriver> getTaxiDrivers() {
        return taxiDrivers;
    }

    public void setTaxiDrivers(Collection<TaxiDriver> taxiDriverHasDriveTypesById) {
        this.taxiDrivers = taxiDriverHasDriveTypesById;
    }
}
