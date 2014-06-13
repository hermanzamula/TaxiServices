package com.taxiservice.web.request;

import com.taxiservice.model.writer.CarpoolManagement;

/**
 * @author Herman Zamula
 */
public class TripCreationRequest {
    public Long id;
    public CarpoolManagement.TripInfo tripInfo;
    public Long car;
}
