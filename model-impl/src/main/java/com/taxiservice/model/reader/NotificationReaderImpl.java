package com.taxiservice.model.reader;

import com.google.common.base.Predicate;
import com.taxiservice.model.entity.Message;
import com.taxiservice.model.entity.User;
import com.taxiservice.model.repository.NotificationRepository;
import com.taxiservice.model.util.Transformers;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;

/**
 * @author Herman Zamula
 */
@Service
public class NotificationReaderImpl implements NotificationReader<Long> {

    @Inject
    private NotificationRepository notificationRepository;

    @Override
    public Set<NotificationLine> readInboxNotifications(Long actor) {

        return from(notificationRepository.findByRecipient(new User(actor)))
                .transform(Transformers.NOTIFICATION_TRANSFORMER)
                .toSet();
    }

    @Override
    public Set<NotificationLine> readInboxNotifications(@NotNull Long actor, final Date from, final Date to) {

        return from(notificationRepository.findByRecipient(new User(actor)))
                .filter(withinDates(from, to))
                .transform(Transformers.NOTIFICATION_TRANSFORMER)
                .toSet();
    }

    @Override
    public Set<NotificationLine> readOutboxNotifications(@NotNull Long actor, Date from, Date to) {

        return from(notificationRepository.findBySender(new User(actor)))
                .filter(withinDates(from, to))
                .transform(Transformers.NOTIFICATION_TRANSFORMER)
                .toSet();
    }

    private Predicate<Message> withinDates(final Date from, final Date to) {
        return new Predicate<Message>() {
            @Override
            public boolean apply(Message input) {
                return input.getDate().compareTo(from) >= 0
                        && input.getDate().compareTo(to) <= 0;
            }
        };
    }
}
