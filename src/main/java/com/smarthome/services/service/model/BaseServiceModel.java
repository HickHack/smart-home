package com.smarthome.services.service.model;

/**
 * Created by graham on 28/03/17.
 */
public class BaseServiceModel {

    private int servicePort;
    private String serviceName;
    private String status;

    public BaseServiceModel(String serviceName, int servicePort) {
        this.serviceName = serviceName;
        this.servicePort = servicePort;
    }

    public int getServicePort() {
        return servicePort;
    }

    public String getServiceName() {
        return serviceName;
    }
}
