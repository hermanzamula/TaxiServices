package com.taxiservice.model.internal.repository;


import com.taxiservice.model.internal.entity.City;
import com.taxiservice.model.internal.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Long> {

    @Query("select city from  City city where city.country = :country")
    List<City> findByCountry(@Param("country") Country country);
}
