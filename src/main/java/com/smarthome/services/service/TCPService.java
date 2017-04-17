package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service discovery implementation
 * used to find other services.
 */
public interface TCPService extends Service {
    void addSubscriber(ServiceType subscriberType);
    void setController(ServiceController controller);
    ServiceResponse connectToService(ServiceOperation operation, ServiceType serviceType);
}
