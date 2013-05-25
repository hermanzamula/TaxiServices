package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;


@javax.persistence.Table(name = "user_place")
@Entity
public class UserPlace extends AbstractPersistable<Long> {

    private Date lastModification;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @javax.persistence.JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @javax.persistence.JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public UserPlace() {
    }

    public UserPlace(City city, User user, Date date) {
        this.city = city;
        this.user = user;
        this.lastModification = date;
    }

    @javax.persistence.Column(name = "lastModification", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
