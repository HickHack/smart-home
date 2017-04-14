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
    private BaseServiceModel model;

    public ServiceResponse(Status status, BaseServiceModel model) {
        this.status = status;
        this.model = model;
        timestamp = new Date().toString();
    }

    public void getModel() {
        this.model = model;
    }

    public Status getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
