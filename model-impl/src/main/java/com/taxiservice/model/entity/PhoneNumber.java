package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@Embeddable
public class PhoneNumber {

    public String number;

}
