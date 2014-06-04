/*
 * C O P Y R I G H T   N O T I C E
 * -----------------------------------------------------------------------
 * Copyright (c) 2011-2012 InfoClinika, Inc. 5901 152nd Ave SE, Bellevue, WA 98006,
 * United States of America.  (425) 442-8058.  http://www.infoclinika.com.
 * All Rights Reserved.  Reproduction, adaptation, or translation without prior written permission of InfoClinika, Inc. is prohibited.
 * Unpublished--rights reserved under the copyright laws of the United States.  RESTRICTED RIGHTS LEGEND Use, duplication or disclosure by the
 */
package com.taxiservice.model;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
class NotifierImpl implements Notifier {
    @Inject
    private MailSendingHelper mailSendingHelper;
    @Inject
    private Emailer emailer;

    @Inject
    private VelocityEngine velocityEngine;



    private void send(String to, String template, Map<String, Object> model) {
        String message;
        try {
            message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, model);
        } catch (VelocityException e) {
            throw new RuntimeException("Could not compose message from template " + template + " with model " + model, e);
        }
        int firstLineEnd = message.indexOf('\n');
        String subject = message.substring(0, firstLineEnd);
        message = message.substring(firstLineEnd + 1);
        emailer.send(to, subject, message);
    }


    @Override
    public void sendTaxiOrder(long actor, long driver, long city, Date onDate, String message) {

        Map<String, Object> model = new HashMap<String, Object>();
        final MailSendingHelper.UserDetails value = mailSendingHelper.userDetails(actor);
        model.put("user", value.name);
        model.put("email", value.email);
        model.put("city", mailSendingHelper.cityName(city));
        model.put("date", onDate);
        if(message != null)
        model.put("message", message);

        send(mailSendingHelper.taxiEmail(driver), "taxiOrder.vm", model);
    }
}