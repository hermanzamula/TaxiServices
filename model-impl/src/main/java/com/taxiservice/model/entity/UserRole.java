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
    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
