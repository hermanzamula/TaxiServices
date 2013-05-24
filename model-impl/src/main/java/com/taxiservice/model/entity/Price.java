package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Price extends AbstractPersistable<Long> {

    @Column
    private Double minValue;
    @Column
    private Double maxValue;

    private String description;

    public Price(Double minValue, Double maxValue, String description) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.description = description;
    }

    public Price() {
    }

    @javax.persistence.Column(name = "description", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }
}
