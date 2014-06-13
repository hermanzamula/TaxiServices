package com.taxiservice.model.entity;

import com.google.common.collect.Sets;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Herman Zamula
 */
@Entity
public class Trip extends AbstractPersistable<Long> {

    @Column
    private String name;

    @Column
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lng",
                    column = @Column(name = "start_lng")),
            @AttributeOverride(name = "lat",
                    column = @Column(name = "start_lat"))})
    private Location start;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lng",
                    column = @Column(name = "end_lng")),
            @AttributeOverride(name = "lat",
                    column = @Column(name = "end_lat"))})
    private Location end;

    @ManyToOne
    private Driver driver;

    @Column(name = "passengers_limit")
    private int passengersLimit;

    @Column(name = "start_date")
    private Date startDate;

    @ManyToMany
    @JoinTable(name = "trip_subscribed_user",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    private Set<Passenger> subscribedPassengers = newHashSet();
    private Car car;

    public Set<Passenger> getSubscribedPassengers() {
        return subscribedPassengers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getPassengersLimit() {
        return passengersLimit;
    }

    public void setPassengersLimit(int passengersLimit) {
        this.passengersLimit = passengersLimit;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}