package com.taxiservice.model.repository;

import com.taxiservice.model.entity.Message;
import com.taxiservice.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Herman Zamula
 */
public interface NotificationRepository extends CrudRepository<Message, Long> {

    List<Message> findBySender(User actor);

    List<Message> findByRecipient(User actor);
}
