package com.taxiservice.model.internal.repository;


import com.taxiservice.model.internal.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {
}
