package com.smarthome.services.service.mqtt;

import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceResponse;
import org.eclipse.paho.client.mqttv3.MqttCallback;

/**
 * @author Graham Murray
 */
public interface MQTTService extends Service {

    /**
     * Push a message to a topic. This is used in
     * service controllers
     *
     * @param message
     */
    void publish(Object message);

    /**
     * Add a subscribe to topics by calling
     * subscribe on MQTTOperations
     */
    void subscribe();
}
