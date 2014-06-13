package com.taxiservice.model.entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;


@Table(name = "driver")
@Indexed
@Entity
@SecondaryTable(name = "driver_location")
public class Driver extends UserRole implements Located {

    @ElementCollection
    @CollectionTable(name = "driver_phone", joinColumns = @JoinColumn(name = "driver_id"))
    private Collection<PhoneNumber> phoneNumbers = newHashSet();

    @OneToMany
    private Set<Trip> trips = newHashSet();

    @OneToMany(mappedBy = "driver")
    private Set<Car> cars = newHashSet();

    @Column
    private long rate = 0;
    @Column
    private long ratedCount = 0;
    @Lob
    @Field
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat",
                    column = @Column(name = "lat", table = "driver_location")),
            @AttributeOverride(name = "lng",
                    column = @Column(name = "lng", table = "driver_location"))})
    private Location currentLocation;


    @OneToMany(mappedBy = "driver", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Comment> comments = newHashSet();

    public Driver(Long id) {
        super.setId(id);
    }

    protected Driver() {

    }

    public Driver(String description, Collection<PhoneNumber> phoneNumbers) {
        this.setDescription(description);
        this.phoneNumbers = phoneNumbers;
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

    public Set<Comment> getComments() {
        return comments;
    }

    @Override
    @Transient
    public Location getLocation() {
        return getCurrentLocation();
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public long getRatedCount() {
        return ratedCount;
    }

    public void setRatedCount(long ratedCount) {
        this.ratedCount = ratedCount;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
