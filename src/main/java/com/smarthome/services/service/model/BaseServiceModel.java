package com.smarthome.services.service.model;

import java.util.Map;

/**
 * Created by graham on 28/03/17.
 */
public abstract class BaseServiceModel {

    private int servicePort;
    private String serviceName;
    private String status;

    public BaseServiceModel(String serviceName, int servicePort) {
        this.serviceName = serviceName;
        this.servicePort = servicePort;
    }

    public abstract Map getValuesMap();

    public int getServicePort() {
        return servicePort;
    }

    public String getServiceName() {
        return serviceName;
    }
}
