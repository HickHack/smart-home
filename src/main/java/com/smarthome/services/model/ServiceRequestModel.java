package com.smarthome.services.model;

import com.google.gson.Gson;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service controller
 */
public class ServiceRequestModel {

    private String serviceName;
    private long serviceUID;
    private int operation;

    public ServiceRequestModel(int operation, String serviceName, long serviceUID) {
        this.serviceName = serviceName;
        this.serviceUID = serviceUID;
        this.operation = operation;
    }

    public int getOperation() {
        return operation;
    }

    public String getServiceName() {
        return serviceName;
    }

    public long getServiceUID() {
        return serviceUID;
    }

}
