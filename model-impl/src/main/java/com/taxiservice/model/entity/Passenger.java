package com.taxiservice.model.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author Herman Zamula
 */
@Entity
@Table(name = "passenger")
@SecondaryTable(name = "passenger_location")
public class Passenger extends UserRole {

    public String description;

    public long rate;

    @ElementCollection
    @CollectionTable(name = "passenger_phone_number", joinColumns = @JoinColumn(name = "passenger_id"))
    public Collection<PhoneNumber> phoneNumbers;

    @ManyToMany(mappedBy = "subscribedPassengers")
    public Collection<Trip> trips;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat",
                    column = @Column(name = "lat", table = "passenger_location")),
            @AttributeOverride(name = "lng",
                    column = @Column(name = "lng", table = "passenger_location"))})
    public Location currentLocation;


}
