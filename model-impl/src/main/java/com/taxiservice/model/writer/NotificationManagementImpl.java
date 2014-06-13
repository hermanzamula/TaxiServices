package com.taxiservice.model.writer;

import com.taxiservice.model.entity.Message;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Herman Zamula
 */
@Service
public class NotificationManagementImpl implements NotificationManagement<Long> {

    @Inject
    private NotificationRepository notificationRepository;

    @Override
    public Long sendMessage(Long actor, Long to, MessageInfo messageInfo) {
        final Message message = new Message();
        message.setBody(checkNotNull(messageInfo.body));
        message.setDate(new Date());
        message.setHeading(messageInfo.heading);
        message.setSender(new User(actor));
        message.setRecipient(new User(to));
        return notificationRepository.save(message).getId();
    }
}
