package com.taxiservice.model.repository;

import com.taxiservice.model.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
