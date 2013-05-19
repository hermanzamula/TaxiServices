package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;


@Table(name = "taxi_driver", schema = "", catalog = "taxiservice")
@Entity
public class TaxiDriver extends AbstractPersistable<Long> {

    private String name;
    @OneToMany(mappedBy = "taxiDriver")
    private Collection<PhoneNumber> phoneNumbers;
    private City city;
    @OneToMany(mappedBy = "taxiDriver")
    private Collection<UserBonus> userBonuses;
    @ManyToMany
    @JoinTable(name = "taxi_driver_has_drive_type")
    private Collection<DriveType> driveTypes;

    public TaxiDriver(Long id) {
    }


    public TaxiDriver() {
    }

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 256, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Collection<PhoneNumber> phoneNumbersById) {
        this.phoneNumbers = phoneNumbersById;
    }

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City cityByCityId) {
        this.city = cityByCityId;
    }

    public Collection<UserBonus> getUserBonuses() {
        return userBonuses;
    }

    public void setUserBonuses(Collection<UserBonus> userBonusesById) {
        this.userBonuses = userBonusesById;
    }

    public Collection<DriveType> getDriveTypes() {
        return driveTypes;
    }

    public void setDriveTypes(Collection<DriveType> taxiDriverHasDriveTypesById) {
        this.driveTypes = taxiDriverHasDriveTypesById;
    }
}
