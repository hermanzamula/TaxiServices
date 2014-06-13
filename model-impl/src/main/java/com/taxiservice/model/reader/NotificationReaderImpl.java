package com.taxiservice.model.reader;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @author Herman Zamula
 */
@Service
public class NotificationReaderImpl implements NotificationReader<Long> {
    @Override
    public Set<NotificationLine> readNotifications(Long actor) {
        return null;
    }

    @Override
    public Set<NotificationLine> readNotifications(@NotNull Long actor, Date from, Date to) {
        return null;
    }
}
