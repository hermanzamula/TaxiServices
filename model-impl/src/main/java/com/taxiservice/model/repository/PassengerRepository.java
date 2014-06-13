package com.taxiservice.model.repository;

import com.taxiservice.model.entity.Passenger;
import com.taxiservice.model.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Herman Zamula
 */
public interface PassengerRepository extends CrudRepository<Passenger, Long> {

    @Query("select p from  Passenger  p where :trip in indices(p.trips) ")
    List<Passenger> findByTrip(@Param("trip") Long trip);

    @Query("select p from Passenger p where p.user = :user")
    Passenger findByUser(@Param("user")User user);
}
