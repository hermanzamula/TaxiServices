package com.taxiservice.model.writer;


public interface UserManagement {

    long createUser(UserInfo userData, String password);

    void addUserToPlace(long actor, long city);

    void removeUser(long userId);

    void updateUserInfo(long userId, UserInfo userInfo);

    void updatePassword(long user, String oldPassword, String password);

    public static class UserInfo {
        public final String firstName;
        public final String lastName;
        public final String email;
        public final long id;
        public final Place place;

        public UserInfo(long id, String lastName, String email, String firstName, Place place) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.place = place;
        }

        public UserInfo(String firstName, String lastName, String email, Place place) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.place = place;
            this.id = 0;
        }
    }


    public static class Place {
        public final long city;
        public final long country;

        public Place(long city, long country) {
            this.city = city;
            this.country = country;
        }
    }
}
