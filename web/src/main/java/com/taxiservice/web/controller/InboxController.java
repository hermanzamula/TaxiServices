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
@RequestMapping("/inbox")
public class InboxController extends BasicSecurityController {

    @Inject
    private NotificationReader<Long> notificationReader;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<NotificationReader.NotificationLine> getAll(@RequestParam String token, @RequestParam(required = false) Date from, @RequestParam(required = false) Date to) {
        if (from == null || to == null) {
            return notificationReader.readInboxNotifications(getUserId(token));
        }
        return notificationReader.readInboxNotifications(getUserId(token), from, to);
    }

}
