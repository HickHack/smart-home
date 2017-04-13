package com.smarthome.services.service;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.Date;

/**
 * Created by graham on 28/03/17.
 */
public class ServiceResponse {

    BaseServiceModel model;
    private String timestamp;

    public ServiceResponse(BaseServiceModel model) {
        this.model = model;
        timestamp = new Date().toString();
    }

    public BaseServiceModel getModel() {
        return model;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
