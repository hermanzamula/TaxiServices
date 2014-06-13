package com.taxiservice.model.writer;

import org.springframework.stereotype.Service;

/**
 * @author Herman Zamula
 */
@Service
public class NotificationManagementImpl implements NotificationManagement<Long> {
    @Override
    public Long sendMessage(Long actor, Long to, MessageInfo messageInfo) {
        return null;
    }
}
