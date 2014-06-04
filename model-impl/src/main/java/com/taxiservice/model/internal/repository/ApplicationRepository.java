package com.taxiservice.model.internal.repository;

import com.taxiservice.model.internal.entity.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Herman Zamula
 */
@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {
}
