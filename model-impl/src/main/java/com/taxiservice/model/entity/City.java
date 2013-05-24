package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class City extends AbstractPersistable<Long> {
    private String name;


    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Collection<TaxiDriver> taxiDrivers;

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
}
