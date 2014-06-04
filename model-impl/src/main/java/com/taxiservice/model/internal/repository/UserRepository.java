package com.taxiservice.model.internal.repository;


import com.taxiservice.model.internal.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.email = :email")
    public User findOneByEmail(@Param("email") String email);

    @Query("select u from User u where u.email = :email and u.passwordHash = :passwordHash")
    public User findOneByEmailAndPasswordHash(@Param("email") String email, @Param("passwordHash") String passwordHash);

}
