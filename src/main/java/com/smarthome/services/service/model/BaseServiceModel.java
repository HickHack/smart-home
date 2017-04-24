package com.smarthome.services.service.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Graham Murray
 * @description Base service model that is extended
 * by specific service models
 */
public class BaseServiceModel {

    private int servicePort;
    private String serviceName;

    public BaseServiceModel(String serviceName, int servicePort) {
        this.serviceName = serviceName;
        this.servicePort = servicePort;
    }

    public Map getValuesMap() {
        Map valuesMap = new HashMap();
        valuesMap.put("Port", servicePort);
        valuesMap.put("Name", serviceName);

        return valuesMap;
    }

    public int getServicePort() {
        return servicePort;
    }

    public String getServiceName() {
        return serviceName;
    }
}
