package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.math.BigInteger;

@Table(name = "user_bonus", schema = "", catalog = "taxiservice")
@Entity
public class UserBonus extends AbstractPersistable<Long> {

    private String description;

    public UserBonus(Long id) {
        setId(id);
    }

    public UserBonus() {
    }

    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 256, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private BigInteger value;

    @Column(name = "value", nullable = false, insertable = true, updatable = true, length = 2, precision = 0)
    @Basic
    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public UserBonus(String description, BigInteger value, TaxiDriver taxiDriver, User user, DriveType driveType) {
        this.description = description;
        this.value = value;
        this.taxiDriver = taxiDriver;
        this.user = user;
        this.driveType = driveType;
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

    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User userByUserId) {
        this.user = userByUserId;
    }

    private DriveType driveType;

    @ManyToOne
    @JoinColumn(name = "drive_type_id", referencedColumnName = "id", nullable = false)
    public DriveType getDriveType() {
        return driveType;
    }

    public void setDriveType(DriveType driveTypeByDriveTypeId) {
        this.driveType = driveTypeByDriveTypeId;
    }
}
