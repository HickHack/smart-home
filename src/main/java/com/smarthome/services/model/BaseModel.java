package com.smarthome.services.model;

/**
 * Created by graham on 28/03/17.
 */
public class BaseModel {

    private int servicePort;
    private long UUID;
    private String serviceName;

    public BaseModel(int servicePort, long UUID, String serviceName) {
        this.servicePort = servicePort;
        this.UUID = UUID;
        this.serviceName = serviceName;
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
}
