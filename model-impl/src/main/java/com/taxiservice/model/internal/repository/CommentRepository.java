package com.taxiservice.model.internal.repository;

import com.taxiservice.model.internal.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
