package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.math.BigInteger;

@Table(name = "user_bonus")
@Entity
public class UserBonus extends AbstractPersistable<Long> {

    @Basic
    private String description;
    @Basic(optional = false)
    private BigInteger value;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id", nullable = false)
    private Price price;

    public UserBonus(Long id) {
        setId(id);
    }

    public UserBonus() {
    }

    public UserBonus(String description, BigInteger value, User user, Price price) {
        this.description = description;
        this.value = value;
        this.user = user;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User userByUserId) {
        this.user = userByUserId;
    }

    public Price getPrice() {
        return price;
    }
}
