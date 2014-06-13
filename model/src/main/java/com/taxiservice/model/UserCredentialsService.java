package com.taxiservice.model;


import org.springframework.transaction.annotation.Transactional;

public interface UserCredentialsService<ID> {

    UserData<ID> checkCredentials(String email, String passwordHash);

    class UserData<ID> {

        public final ID id;
        public final String firstName;
        public final String lastName;
        public final String email;

        public UserData(ID id, String lastName, String email, String firstName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
    }
}
