package com.taxiservice.model.entity;

import com.google.common.collect.ImmutableSet;
import org.hibernate.search.annotations.Indexed;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;


@Table(name = "taxi_driver", schema = "", catalog = "taxiservice")
@Indexed
@Entity
public class TaxiDriver extends AbstractPersistable<Long> {

    private String name;
    @OneToMany(mappedBy = "taxiDriver", fetch = FetchType.EAGER)
    private Collection<PhoneNumber> phoneNumbers = newHashSet();
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;
    @OneToMany(mappedBy = "taxiDriver")
    private Collection<UserBonus> userBonuses = newHashSet();

    @Column
    private long rate = 0;
    @Column
    private String site = null;
    @Column
    private String description;

    @OneToMany(mappedBy = "taxiDriver")
    private Collection<TaxiDriverHasDriveType> prices = newHashSet();

    @ManyToMany
    @JoinTable(name = "taxi_driver_has_drive_type")
    private Collection<DriveType> driveTypes = newHashSet();

    public TaxiDriver(Long id) {
    }

    public TaxiDriver() {
    }

    public TaxiDriver(String name, City city, String site, String description) {
        this.name = name;
        this.description = description;
        this.phoneNumbers = phoneNumbers;
        this.city = city;
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

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<TaxiDriverHasDriveType> getPrices() {
        return prices;
    }

    public Collection<DriveType> getDriveTypes() {
        return driveTypes;
    }
}
