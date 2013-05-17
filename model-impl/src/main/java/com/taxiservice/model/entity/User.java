package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class User extends AbstractPersistable<Integer> {


    private String firstName;

    @Column(name = "firstName", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String email;

    @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String passwordHash;

    @Column(name = "passwordHash", nullable = false, insertable = true, updatable = true, length = 128, precision = 0)
    @Basic
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    private String lastName;

    @Column(name = "lastName", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private Collection<UserBonus> bonuses;

    @OneToMany(mappedBy = "user")
    public Collection<UserBonus> getBonuses() {
        return bonuses;
    }

    public void setBonuses(Collection<UserBonus> userBonusesById) {
        this.bonuses = userBonusesById;
    }

    public User(String firstName, String lastName, String email, String passwordHash) {
        this.firstName = firstName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.lastName = lastName;
    }
}
