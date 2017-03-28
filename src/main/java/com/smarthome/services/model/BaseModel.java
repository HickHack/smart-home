package com.smarthome.services.model;

/**
 * Created by graham on 28/03/17.
 */
public class BaseModel {

    private int servicePort;
    private long UUID;
    private String serviceName;

    public BaseModel() {

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
