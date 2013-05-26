package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;


@Entity
public class User extends AbstractPersistable<Long> {


    @Column(name = "isAdmin", nullable = false, insertable = true, updatable = true)
    @Basic
    private boolean isAdmin;
    private String firstName;
    @Column(unique = true)
    @Basic
    private String email;
    private String passwordHash;
    private String lastName;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<UserBonus> bonuses = newHashSet();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "favorite", joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "taxi_driver_id", referencedColumnName = "id"))
    private Collection<TaxiDriver> favourites = newHashSet();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_place", joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
    private Collection<City> userPlaces = newHashSet();

    public User(Long id) {
        setId(id);
    }

    public User() {
    }

    public User(String firstName, String lastName, String email, String passwordHash) {
        this.firstName = firstName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.lastName = lastName;
    }

    @Column(name = "firstName", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "passwordHash", nullable = false, insertable = true, updatable = true, length = 128, precision = 0)
    @Basic
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Column(name = "lastName", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<UserBonus> getBonuses() {
        return bonuses;
    }

    public Collection<TaxiDriver> getFavourites() {
        return favourites;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Collection<City> getUserPlaces() {
        return userPlaces;
    }


}
