package com.taxiservice.model;

public interface MailSendingHelper {

    String taxiEmail(long id);

    UserDetails  userDetails(long id);

    String cityName(long id);

    class UserDetails {
        public final String name;
        public final String email;

        public UserDetails(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
}
