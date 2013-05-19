package com.taxiservice.model.repository;

import com.google.common.collect.ImmutableList;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.entity.UserPlace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserPlaceRepository extends CrudRepository<UserPlace, Long> {

    @Query("select u from UserPlace up where up.user = :user order by up.lastModification")
    ImmutableList<UserPlace> findByUser(@Param("user") User one);
}
