package com.taxiservice.web.security;

import com.taxiservice.model.AuthenticationService;
import com.taxiservice.model.writer.UserManagement;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = Logger.getLogger(UserAuthenticationProvider.class);

    @Inject
    private AuthenticationService authenticationService;

    public static final List<GrantedAuthority> AUTHORITY_LIST = createAuthorityList("USER_ROLE");

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken usernamePassword
                = (UsernamePasswordAuthenticationToken) authentication;

        String email = String.valueOf(usernamePassword.getPrincipal());
        String password = String.valueOf(usernamePassword.getCredentials());

        final UserManagement.UserInfo user;
        try {
            user = authenticationService.checkCredentials(email, password);
        } catch (Exception e) {
            logger.debug("User is not authenticated", e);
            return null;
        }

        RichUser principal = new RichUser(user.id, user.email, AUTHORITY_LIST);

        return new UsernamePasswordAuthenticationToken(principal, null, AUTHORITY_LIST);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
