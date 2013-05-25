package com.taxiservice.model.repository;


import com.taxiservice.model.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {
}
