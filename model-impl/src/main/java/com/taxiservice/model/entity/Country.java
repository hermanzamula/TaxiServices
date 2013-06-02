package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;


@Entity
public class Country extends AbstractPersistable<Long> {
    private String name;
    public Country() {
    }

    public Country(Long id) {
        setId(id);
    }

    public Country(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
