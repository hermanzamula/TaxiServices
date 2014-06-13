package com.taxiservice.model.reader;

import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @author Herman Zamula
 */
@Transactional(readOnly = true)
public interface NotificationReader<ID> {

    Set<NotificationLine> readNotifications(@NotNull ID actor);

    Set<NotificationLine> readNotifications(@NotNull ID actor, Date from, Date to);

    class NotificationLine {
    }
}
