package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service discovery implementation
 * used to find other services.
 */
public interface TCPService extends Service {
    void register();
    void addSubscriber(ServiceType subscriberType);
    void setController(ServiceController controller);
    String connectToService(ServiceOperation operation, ServiceType serviceType);
}
