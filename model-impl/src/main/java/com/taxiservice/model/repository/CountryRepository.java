package com.taxiservice.model.repository;


import com.taxiservice.model.entity.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long>{
}
