package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;


@Table(name = "drive_type")
@Entity
public class DriveType extends AbstractPersistable<Long> {


    private String name;
    private String description;
    @OneToMany(mappedBy = "driveType", cascade = CascadeType.ALL)
    private Collection<UserBonus> userBonuses = newHashSet();
    @OneToMany(mappedBy = "driveType", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<Price> taxiDrivers = newHashSet();

    public DriveType() {
    }

    public DriveType(Long id) {
        setId(id);
    }

    public DriveType(String name, String description, Collection<Price> taxiDrivers, Collection<UserBonus> userBonuses) {
        this.name = name;
        this.description = description;
        this.taxiDrivers = taxiDrivers;
        this.userBonuses = userBonuses;
    }

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 1024)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Collection<UserBonus> getUserBonuses() {
        return userBonuses;
    }

    public Collection<Price> getTaxiDrivers() {
        return taxiDrivers;
    }

}
