package com.smarthome.services.service.mqtt;

import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceController;
import com.smarthome.services.service.ServiceResponse;

/**
 * @author Graham Murray
 */
public interface MQTTService extends Service {
    void publish(ServiceResponse response);
    void subscribe();
    ServiceController getController();
}
