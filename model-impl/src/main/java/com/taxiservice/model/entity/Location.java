package com.taxiservice.model.entity;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

/**
 * @author Herman Zamula
 */
@Embeddable
public class Location {

    @Basic
    public float lat;
    @Basic
    public float lng;

}
