package com.taxiservice.model.internal.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;


@Table(name = "drive_type")
@Entity
public class DriveType extends AbstractPersistable<Long> {


    private String name;

    @Column(name = "description", length = 1024)
    @Basic
    private String description;
    @OneToMany(mappedBy = "driveType", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<Price> prices = newHashSet();

    public DriveType() {
    }

    public DriveType(Long id) {
        setId(id);
    }

    public DriveType(String name, String description, Collection<Price> taxiDrivers) {
        this.name = name;
        this.description = description;
        this.prices = taxiDrivers;
    }

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Price> getPrices() {
        return prices;
    }

}
