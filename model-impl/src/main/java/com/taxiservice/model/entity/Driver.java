package com.taxiservice.model.entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;


@Table(name = "driver")
@Indexed
@Entity
@SecondaryTable(name = "driver_location")
public class Driver extends UserRole {

    @ElementCollection
    @CollectionTable(name = "driver_phone", joinColumns = @JoinColumn(name = "driver_id"))
    private Collection<PhoneNumber> phoneNumbers = newHashSet();

    @OneToMany
    public Collection<Trip> trips;

    @OneToMany(mappedBy = "driver")
    public Collection<Car> cars;

    @Column
    long rate = 0;
    @Lob
    @Field
    public String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat",
                    column = @Column(name = "lat", table = "driver_location")),
            @AttributeOverride(name = "lng",
                    column = @Column(name = "lng", table = "driver_location"))})
    public Location currentLocation;


    @OneToMany(mappedBy = "driver", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Collection<Comment> comments = newHashSet();

    public Driver(Long id) {
    }

    public Driver() {
    }

    public Driver(String name, String site, String description) {
        this.description = description;
    }


    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

}
