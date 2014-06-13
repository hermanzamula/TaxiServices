package com.taxiservice.model.entity;

import org.hibernate.type.CurrencyType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author Herman Zamula
 */
@Embeddable
public class PaymentInfo {
    public CurrencyType currency;
    @Column(updatable = false, insertable = false)
    public Long totalPayed;
}
