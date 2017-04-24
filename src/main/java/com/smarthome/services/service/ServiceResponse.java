package com.smarthome.services.service;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.Date;

/**
 * @author Graham Murray
 * @description Service response model used when responding to
 * a service.
 */
public class ServiceResponse {

    /**
     * Status codes to used by the invoking
     * service to decide if the operation
     * performed was a success
     */
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
