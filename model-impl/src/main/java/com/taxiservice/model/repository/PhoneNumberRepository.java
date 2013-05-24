package com.taxiservice.model.repository;

import com.taxiservice.model.entity.PhoneNumber;
import org.springframework.data.repository.CrudRepository;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
}
