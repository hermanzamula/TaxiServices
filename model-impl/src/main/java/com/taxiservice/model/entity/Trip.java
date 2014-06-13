package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * @author Herman Zamula
 */
@Entity
public class Trip extends AbstractPersistable<Long> {

    @Column
    public String name;

    @Column
    public String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lng",
                    column = @Column(name = "start_lng")),
            @AttributeOverride(name = "lat",
                    column = @Column(name = "start_lat"))})
    public Location start;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lng",
                    column = @Column(name = "end_lng")),
            @AttributeOverride(name = "lat",
                    column = @Column(name = "end_lat"))})
    public Location end;

    @ManyToOne
    public Driver driver;

    @Column(name = "passengers_limit")
    public int passengersLimit;

    @Column(name = "start_date")
    public Date startDate;

    @ManyToMany
    @JoinTable(name = "trip_subscribed_user",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    public Collection<Passenger> subscribedPassengers;


}
