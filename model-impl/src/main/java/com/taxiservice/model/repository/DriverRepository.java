package com.taxiservice.model.repository;


import com.taxiservice.model.entity.Driver;
import com.taxiservice.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    @Query("select d from  Driver  d where d.user = :user")
    Driver findByUser(@Param("user")User actor);
}
