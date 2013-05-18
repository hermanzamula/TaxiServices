package com.taxiservice.model.repository;


import com.taxiservice.model.entity.TaxiDriver;
import org.springframework.data.repository.CrudRepository;

public interface TaxiDriverRepository extends CrudRepository<TaxiDriver, Long> {
}
