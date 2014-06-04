package com.taxiservice.model.internal.repository;

import com.taxiservice.model.internal.entity.AccessToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Herman Zamula
 */
@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, Long> {

    @Query("select at from AccessToken at where at.application.id = :app and at.user.id = :user")
    AccessToken findByUserAndApplication(@Param("user") long user, @Param("app") long app);
}
