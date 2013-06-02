package com.taxiservice.model.repository;


import com.taxiservice.model.entity.City;
import com.taxiservice.model.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Long> {

    @Query("select c from  City city where city.country = :country")
    List<City> findByCountry(@Param("country") Country country);
}
