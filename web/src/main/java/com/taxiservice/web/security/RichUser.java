package com.taxiservice.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Herman Zamula
 */
public class RichUser extends User {

    private long id;

    public RichUser(long id, String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
        this.id = id;
        this.eraseCredentials();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
