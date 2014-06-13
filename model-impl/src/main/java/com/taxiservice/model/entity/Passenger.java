package com.taxiservice.model.entity;

import javax.persistence.*;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Herman Zamula
 */
@Entity
@Table(name = "passenger")
@SecondaryTable(name = "passenger_location")
public class Passenger extends UserRole implements Located {

    private String description;

    private long rate;

    @ElementCollection
    @CollectionTable(name = "passenger_phone_number", joinColumns = @JoinColumn(name = "passenger_id"))
    private Set<PhoneNumber> phoneNumbers = newHashSet();

    @ManyToMany(mappedBy = "subscribedPassengers")
    private Set<Trip> trips = newHashSet();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat",
                    column = @Column(name = "lat", table = "passenger_location")),
            @AttributeOverride(name = "lng",
                    column = @Column(name = "lng", table = "passenger_location"))})
    private Location currentLocation;
    @Column(name = "rated_count")
    private int ratedCount = 0;

    protected Passenger() {
    }

    public Passenger(Long id) {
        super.setId(id);
    }

    public Passenger(User user, Set<PhoneNumber> phoneNumbers) {
        setUser(user);
        getPhoneNumbers().addAll(phoneNumbers);
    }


    @Override
    @Transient
    public Location getLocation() {
        return getCurrentLocation();
    }

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getRatedCount() {
        return ratedCount;
    }

    public void setRatedCount(int ratedCount) {
        this.ratedCount = ratedCount;
    }
}
