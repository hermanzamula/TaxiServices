package com.taxiservice.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "favorite")
@Entity
public class Favorite implements Serializable {


    @Id
    @ManyToOne
    @javax.persistence.JoinColumn(name = "taxi_driver_id", referencedColumnName = "id", nullable = false)
    private TaxiDriver taxiDriver;

    @Id
    @ManyToOne
    @javax.persistence.JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


    public TaxiDriver getTaxiDriver() {
        return taxiDriver;
    }

    public void setTaxiDriver(TaxiDriver taxiDriverByTaxiDriverId) {
        this.taxiDriver = taxiDriverByTaxiDriverId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User userByUserId) {
        this.user = userByUserId;
    }
}
