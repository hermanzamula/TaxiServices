package com.taxiservice.model;

import java.util.Date;

public interface Notifier {

    void sendTaxiOrder(long actor, long driver, long city, Date onDate, String message);
}
