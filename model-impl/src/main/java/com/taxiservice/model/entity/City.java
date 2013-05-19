package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class City extends AbstractPersistable<Long> {
    private String name;

    public City() {
    }

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Country country;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    public Country getCountry() {
        return country;
    }

    public City(Long id) {
        setId(id);
    }

    public void setCountry(Country countryByCountryId) {
        this.country = countryByCountryId;
    }

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Collection<TaxiDriver> taxiDrivers;

    public Collection<TaxiDriver> getTaxiDrivers() {
        return taxiDrivers;
    }

    public void setTaxiDrivers(Collection<TaxiDriver> taxiDriversById) {
        this.taxiDrivers = taxiDriversById;
    }
}
