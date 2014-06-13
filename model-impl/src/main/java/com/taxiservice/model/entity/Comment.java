package com.taxiservice.model.entity;


import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment extends AbstractPersistable<Long> {

    @Lob
    @Basic(optional = false)
    String message;

    @Basic(optional = false)
    Date date;

    @ManyToOne
    public User user;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    Driver driver;

    public Comment(String message, User user, Driver driver) {
        this.message = message;
        this.date = new Date();
        this.user = user;
        this.driver = driver;
    }

    public Comment() {
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
