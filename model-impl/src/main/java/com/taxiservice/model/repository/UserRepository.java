package com.taxiservice.model.repository;


import com.taxiservice.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select user from User u where u.email = :email")
    public User findOneByEmail(@Param("email") String email);

    @Query("select user from User u where u.email = :email and u.passwordHash = :passwordHash")
    public User findOneByEmailAndPasswordHash(@Param("email") String email, @Param("passwordHash") String passwordHash);

}
