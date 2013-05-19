package com.taxiservice.model.repository;


import com.taxiservice.model.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
}
