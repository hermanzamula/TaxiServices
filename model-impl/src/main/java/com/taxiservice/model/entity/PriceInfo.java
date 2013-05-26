package com.taxiservice.model.entity;


import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Embeddable
public class PriceInfo {


    private static final DecimalFormat FORMAT = new DecimalFormat(".##");

    @Basic(optional = false)
    BigDecimal minimum;
    @Basic(optional = false)
    BigDecimal maximum;
    @Basic
    String description;
    @Basic
    String currency = "UAH";

    public PriceInfo(BigDecimal minimum, BigDecimal maximum, String description) {
        this.minimum =  minimum;
        this.maximum = maximum;
        this.description = description;
    }

    public PriceInfo() {
    }

    public BigDecimal getMinimum() {
        return minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    public BigDecimal getMaximum() {
        return maximum;
    }

    public void setMaximum(BigDecimal maximum) {
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
