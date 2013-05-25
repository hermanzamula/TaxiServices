package com.taxiservice.model.entity;


import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class PriceInfo {

    @Basic(optional = false)
    long minimum;
    @Basic(optional = false)
    long maximum;
    @Basic
    String description;
    @Basic
    String currency = "UAH";

    public PriceInfo(long minimum, long maximum, String description) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.description = description;
    }

    public PriceInfo() {
    }

    public long getMinimum() {
        return minimum;
    }

    public void setMinimum(long minimum) {
        this.minimum = minimum;
    }

    public long getMaximum() {
        return maximum;
    }

    public void setMaximum(long maximum) {
        this.maximum = maximum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
