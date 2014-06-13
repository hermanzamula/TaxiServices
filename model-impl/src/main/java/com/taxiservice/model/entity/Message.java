package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author Herman Zamula
 */
@Entity
public class Message extends AbstractPersistable<Long> {

    @Lob
    public String body;
    public String heading;
    public Date date;

    @ManyToOne
    public User sender;

    @ManyToOne
    public User recipient;

}
