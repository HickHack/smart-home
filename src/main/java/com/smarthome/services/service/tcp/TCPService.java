package com.smarthome.services.service.tcp;

import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service discovery implementation
 * used to find other services.
 */
public interface TCPService extends Service {

    /**
     * Subscribe to a service so the service can
     * discover the particular. This may be called
     * multiple times to subscribe to many services.
     * @param subscriberType
     */
    void addSubscriber(ServiceType subscriberType);

    /**
     * Connects to a service and attempts to send a request.
     * If the serviceType specified is not yet to be discovered
     * the request is aborted and null is returned. If a request
     * fails to send a retry mechanism is used to resend for
     * the amount of times set in the config. This retry also
     * occurs for timeouts. The timeout threshold is set in the
     * config
     *
     * @param operation
     * @param serviceType
     * @return response from a service
     */
    ServiceResponse connectToService(ServiceOperation operation, ServiceType serviceType);

    int getPort();
}
