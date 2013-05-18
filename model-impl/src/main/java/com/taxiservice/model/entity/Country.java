package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;


@Entity
public class Country extends AbstractPersistable<Long> {
    private String name;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Collection<City> cities;

    @OneToMany(mappedBy = "country")
    public Collection<City> getCities() {
        return cities;
    }

    public void setCities(Collection<City> citiesById) {
        this.cities = citiesById;
    }
}
