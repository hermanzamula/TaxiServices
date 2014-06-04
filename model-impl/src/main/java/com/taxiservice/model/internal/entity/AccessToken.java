package com.taxiservice.model.internal.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Herman Zamula
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "application_id"}))
public class AccessToken extends AbstractPersistable<Long> {

    private String token;
    private Date expires;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    public AccessToken(Application application, User user, String token) {
        this.application = application;
        this.user = user;
        this.token = token;
    }

    public AccessToken() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
