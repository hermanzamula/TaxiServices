package com.taxiservice.model.internal.entity;


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

    @Basic(optional = false)
    String user;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    TaxiDriver driver;

    public Comment(String message, String user, TaxiDriver driver) {
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

    public String getUser() {
        return user;
    }

    public TaxiDriver getDriver() {
        return driver;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
