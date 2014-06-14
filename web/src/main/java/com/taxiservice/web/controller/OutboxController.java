package com.taxiservice.web.controller;

import com.taxiservice.model.reader.NotificationReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Date;
import java.util.Set;

/**
 * @author Herman Zamula
 */
@Controller
@RequestMapping("/outbox")
public class OutboxController extends BasicSecurityController {

    @Inject
    private NotificationReader<Long> notificationReader;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<NotificationReader.NotificationLine> get(@RequestParam String token, @RequestParam Date from, @RequestParam Date to) {
        return notificationReader.readOutboxNotifications(getUserId(token), from, to);
    }

}
