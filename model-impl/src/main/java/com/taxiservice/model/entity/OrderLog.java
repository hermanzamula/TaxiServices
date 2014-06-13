package com.taxiservice.model.entity;

import org.hibernate.type.CurrencyType;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Currency;

/**
 * @author Herman Zamula
 */
@Entity
@Table(name = "order_log")
public class OrderLog extends AbstractPersistable<Long> {

    @ManyToOne
    public Driver driver;
    @ManyToOne
    public Passenger passenger;
    @ManyToOne
    public Trip trip;

    public String description;

    public long totalPayed;

    @Embedded
    public PaymentInfo payed;
}
