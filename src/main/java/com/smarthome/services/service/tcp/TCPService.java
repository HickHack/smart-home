package com.smarthome.services.service.tcp;

import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service discovery implementation
 * used to find other services.
 */
public interface TCPService extends Service {
    void addSubscriber(ServiceType subscriberType);
    ServiceResponse connectToService(ServiceOperation operation, ServiceType serviceType);
    int getPort();
}
