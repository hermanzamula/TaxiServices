package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Herman Zamula
 */
@Entity
public class Car extends AbstractPersistable<Long> {

    public String brand;
    public String description;
    public String model;

    @ManyToOne
    public Driver driver;

    public Car(Long id) {
        super.setId(id);
    }

    protected Car() {
    }
}
