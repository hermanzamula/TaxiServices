package com.taxiservice.model.repository;

import com.taxiservice.model.entity.User;
import com.taxiservice.model.entity.UserPlace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserPlaceRepository extends CrudRepository<UserPlace, Long> {

    @Query("select userPlace from UserPlace where user = :user and lastModification = (select max(lastModification) from user.userPlaces group by lastModification)")
    public  UserPlace findLastModified(@Param(value = "user") User user);
}
