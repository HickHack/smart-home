package com.smarthome.services.service;

import com.smarthome.services.service.model.BaseServiceModel;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service discovery implementation
 * used to find other services.
 */
public interface TCPService extends Service {
    void addSubscriber(ServiceType subscriberType);
    void setController(ServiceController controller);
    BaseServiceModel connectToService(ServiceOperation operation, ServiceType serviceType);
}
