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

    Set<NotificationLine> readInboxNotifications(@NotNull ID actor);

    Set<NotificationLine> readInboxNotifications(@NotNull ID actor, Date from, Date to);

    Set<NotificationLine> readOutboxNotifications(@NotNull ID actor, Date from, Date to);

    class NotificationLine<ID> {
        public final ID id;
        public final String heading;
        public final String body;
        public final String sender;
        public final String recipient;
        public final Date sendDate;

        public NotificationLine(ID id, String heading, String body, String sender, String recipient, Date sendDate) {

            this.id = id;
            this.heading = heading;
            this.body = body;
            this.sender = sender;
            this.recipient = recipient;
            this.sendDate = sendDate;
        }
    }
}
