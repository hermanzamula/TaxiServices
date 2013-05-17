package com.taxiservice.model.repository;


import com.taxiservice.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
