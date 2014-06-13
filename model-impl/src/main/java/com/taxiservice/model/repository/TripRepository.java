package com.taxiservice.model.repository;

import com.taxiservice.model.entity.Trip;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Herman Zamula
 */
public interface TripRepository extends CrudRepository<Trip, Long> {
}
