package com.taxiservice.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Herman Zamula
 */
@Embeddable
public class Location {

    @Column(precision = 6)
    public float lat;
    @Column(precision = 6)
    public float lng;

}
