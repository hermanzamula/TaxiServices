package com.taxiservice.model.writer;

import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * @author Herman Zamula
 */
@Transactional
public interface NotificationManagement<ID> {

    ID sendMessage(@NotNull ID actor, @NotNull ID to, MessageInfo messageInfo);

    class MessageInfo {
        public final String heading;
        public final String body;

        public MessageInfo(String heading, String body) {
            this.heading = heading;
            this.body = body;
        }
    }
}
