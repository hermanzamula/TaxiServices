package com.taxiservice.model.internal.repository;


import com.taxiservice.model.internal.entity.TaxiDriver;
import org.springframework.data.repository.CrudRepository;

public interface TaxiDriverRepository extends CrudRepository<TaxiDriver, Long> {
}
