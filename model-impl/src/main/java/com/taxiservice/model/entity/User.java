package com.taxiservice.model.entity;

import com.google.common.collect.ImmutableSortedSet;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class User extends AbstractPersistable<Long> {

    private boolean isAdmin;
    private String firstName;
    private String email;
    private String passwordHash;
    private String lastName;
    @OneToMany(mappedBy = "user")
    private Collection<UserBonus> bonuses;
    @ManyToMany
    @JoinTable(name = "favorite")
    private Collection<TaxiDriver> userFavourites;

    @OneToMany(mappedBy = "user")
    private Collection<UserPlace> userPlaces;

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

    @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
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

    public void setBonuses(Collection<UserBonus> userBonusesById) {
        this.bonuses = userBonusesById;
    }

    public Collection<TaxiDriver> getUserFavourites() {
        return userFavourites;
    }

    @Column(name = "isAdmin", nullable = false, insertable = true, updatable = true)
    @Basic
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Collection<UserPlace> getUserPlaces() {
        return userPlaces;
    }


}
