package com.taxiservice.model.internal.repository;

import com.taxiservice.model.internal.entity.City;
import com.taxiservice.model.internal.entity.User;
import com.taxiservice.model.internal.entity.UserPlace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPlaceRepository extends CrudRepository<UserPlace, Long> {

    @Query("select up from UserPlace up where up.user = :user order by up.lastModification")
    List<UserPlace> findByUser(@Param("user") User one);

    @Query("select up from UserPlace up where up.city = :city and up.user = :user")
    UserPlace findByUserAndCity(@Param("user") User user, @Param("city") City city);
}
