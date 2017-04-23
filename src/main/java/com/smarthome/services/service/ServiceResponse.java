package com.smarthome.services.service;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.Date;

/**
 * @author Graham Murray
 */
public class ServiceResponse {

    public enum Status {
        OK,
        FAILED,
        UNSUPPORTED_OPERATION
    }

    private Status status;
    private String timestamp;
    private ServiceType type;
    private BaseServiceModel model;

    public ServiceResponse(Status status, BaseServiceModel model, ServiceType type) {
        this.status = status;
        this.model = model;
        this.type = type;
        timestamp = new Date().toString();
    }

    public void setModel(BaseServiceModel model) {
        this.model = model;
    }

    public BaseServiceModel getModel() {
        return this.model;
    }


    public Status getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ServiceType getType() {
        return type;
    }
}
