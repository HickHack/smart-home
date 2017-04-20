package com.smarthome.services.service.mqtt;

import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceResponse;
import org.eclipse.paho.client.mqttv3.MqttCallback;

/**
 * @author Graham Murray
 */
public interface MQTTService extends Service {
    void publish(Object message);
    void subscribe();
}
