package com.taxiservice.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * @author Herman Zamula
 */
@MappedSuperclass
public abstract class UserRole extends AbstractPersistable<Long> {

    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;

}
