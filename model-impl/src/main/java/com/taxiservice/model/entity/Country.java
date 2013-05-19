package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class Country extends AbstractPersistable<Long> {
    private String name;
    @OneToMany(mappedBy = "country",  cascade = CascadeType.ALL)
    private Collection<City> cities;


    public Country() {
    }

    public Country(Long id) {
        setId(id);
    }

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<City> getCities() {
        return cities;
    }

    public void setCities(Collection<City> citiesById) {
        this.cities = citiesById;
    }
}
