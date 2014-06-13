package com.taxiservice.model.repository;


import com.taxiservice.model.entity.Driver;
import org.springframework.data.repository.CrudRepository;

public interface TaxiDriverRepository extends CrudRepository<Driver, Long> {
}
