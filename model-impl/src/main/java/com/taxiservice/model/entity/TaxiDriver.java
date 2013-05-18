package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;


@Table(name = "taxi_driver", schema = "", catalog = "taxiservice")
@Entity
public class TaxiDriver extends AbstractPersistable<Long> {

    private String name;

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 256, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Collection<PhoneNumber> phoneNumbers;

    @OneToMany(mappedBy = "taxiDriver")
    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Collection<PhoneNumber> phoneNumbersById) {
        this.phoneNumbers = phoneNumbersById;
    }

    private City city;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City cityByCityId) {
        this.city = cityByCityId;
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

    private Collection<UserBonus> userBonuses;

    @OneToMany(mappedBy = "taxiDriver")
    public Collection<UserBonus> getUserBonuses() {
        return userBonuses;
    }

    public void setUserBonuses(Collection<UserBonus> userBonusesById) {
        this.userBonuses = userBonusesById;
    }
}
