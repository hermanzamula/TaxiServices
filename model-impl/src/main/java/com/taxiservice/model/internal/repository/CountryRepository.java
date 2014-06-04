package com.taxiservice.model.internal.repository;


import com.taxiservice.model.internal.entity.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long>{
}
