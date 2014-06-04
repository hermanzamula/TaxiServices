package com.taxiservice.web.security;

import com.google.common.base.Function;
import com.taxiservice.model.AuthenticationService;
import com.taxiservice.model.writer.UserManagement;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static com.taxiservice.web.security.UserAuthenticationProvider.AUTHORITY_LIST;

/**
 * @author Herman Zamula
 */
@Component
public class UserRestProvider implements UserDetailsService {

    private final AuthenticationService authenticationService;

    public static final Function<UserManagement.UserInfo,RichUser> TO_RICH_USER = new Function<UserManagement.UserInfo, RichUser>() {
        @Override
        public RichUser apply(UserManagement.UserInfo input) {
            return new RichUser(input.id, input.email, AUTHORITY_LIST);
        }
    };

    @Inject
    public UserRestProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserManagement.UserInfo userInfo = authenticationService.readByEmail(username);
        return TO_RICH_USER.apply(userInfo);
    }
}
