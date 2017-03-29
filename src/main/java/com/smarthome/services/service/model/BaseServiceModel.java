package com.smarthome.services.service.model;

/**
 * Created by graham on 28/03/17.
 */
public class BaseServiceModel {

    private int servicePort;
    private long UUID;
    private String serviceName;

    public BaseServiceModel() {

    }

    public long getUUID() {
        return UUID;
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

    public void setUUID(long UUID) {
        this.UUID = UUID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
