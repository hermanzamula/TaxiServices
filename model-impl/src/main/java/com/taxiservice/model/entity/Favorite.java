package com.taxiservice.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "favorite", schema = "", catalog = "taxiservice")
@Entity
public class Favorite implements Serializable{

    private TaxiDriver taxiDriver;
    private User user;


    @Id
    @ManyToOne
    @javax.persistence.JoinColumn(name = "taxi_driver_id", referencedColumnName = "id", nullable = false)
    public TaxiDriver getTaxiDriver() {
        return taxiDriver;
    }

    public void setTaxiDriver(TaxiDriver taxiDriverByTaxiDriverId) {
        this.taxiDriver = taxiDriverByTaxiDriverId;
    }

    @Id
    @ManyToOne
    @javax.persistence.JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User userByUserId) {
        this.user = userByUserId;
    }
}
