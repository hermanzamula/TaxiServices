package com.taxiservice.model.entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;


@Table(name = "taxi_driver")
@Indexed
@Entity
public class TaxiDriver extends AbstractPersistable<Long> {

    @Basic
    @Field
    private String name;
    @OneToMany(mappedBy = "taxiDriver", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<PhoneNumber> phoneNumbers = newHashSet();
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    City city;

    @Column
    long rate = 0;
    @Column
    String site = null;
    @Lob
    @Field
    String description;

    @OneToMany(mappedBy = "taxiDriver", cascade = CascadeType.ALL)
    private Collection<Price> prices = newHashSet();

    @OneToMany(mappedBy = "driver", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Collection<Comment> comments = newHashSet();

    public TaxiDriver(Long id) {
    }

    public TaxiDriver() {
    }

    public TaxiDriver(String name, City city, String site, String description) {
        this.name = name;
        this.description = description;
        this.site = site;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City cityByCityId) {
        this.city = cityByCityId;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Price> getPrices() {
        return prices;
    }


    public Collection<Comment> getComments() {
        return comments;
    }
}
