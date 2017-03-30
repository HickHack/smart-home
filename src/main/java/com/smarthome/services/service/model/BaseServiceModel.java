package com.smarthome.services.service.model;

/**
 * Created by graham on 28/03/17.
 */
public class BaseServiceModel {

    private int servicePort;
    private String serviceName;

    public BaseServiceModel() {

    }

    public int getServicePort() {
        return servicePort;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
