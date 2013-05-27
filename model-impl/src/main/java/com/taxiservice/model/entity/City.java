package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;


@Entity
public class City extends AbstractPersistable<Long> {
    private String name;


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    @ManyToMany(mappedBy = "cities", cascade = CascadeType.ALL)
    private Collection<TaxiDriver> taxiDrivers = newHashSet();

    @Basic
    private String cityCode;

    public City() {
    }

    public City(Long id) {
        setId(id);
    }

    public City(String name, Country country) {
        this.country = country;
        this.name = name;
    }

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country countryByCountryId) {
        this.country = countryByCountryId;
    }

    public Collection<TaxiDriver> getTaxiDrivers() {
        return taxiDrivers;
    }

    public void setTaxiDrivers(Collection<TaxiDriver> taxiDriversById) {
        this.taxiDrivers = taxiDriversById;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
