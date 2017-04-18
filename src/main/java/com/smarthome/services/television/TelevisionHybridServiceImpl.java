package com.smarthome.services.television;

import com.smarthome.services.service.*;
import com.smarthome.services.service.mqtt.MQTTOperations;
import com.smarthome.services.service.mqtt.MQTTService;
import com.smarthome.services.service.tcp.TCPServiceImpl;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Ian Cunningham
 */
public class TelevisionHybridServiceImpl extends TCPServiceImpl implements MQTTService {

    private MQTTOperations mqttOperations;

    public TelevisionHybridServiceImpl(String name) {
        super(name, ServiceType.TELEVISION);
        mqttOperations = new MQTTOperations(this);
    }

    @Override
    public void run() {
        setController(new TelevisionControllerImpl(this));
        start();
    }

    @Override
    public void publish(ServiceResponse response) {
        try {
            mqttOperations.publish(response, ServiceType.MEDIA_PLAYER);
        } catch (MqttException e) {
            updateUIOutput("Publishing Message to MQTT");
        }
    }

    @Override
    public void subscribe() {
        try {
            mqttOperations.subscribe(ServiceType.MEDIA_PLAYER);
        } catch (MqttException e) {
            updateUIOutput("Failed to send MQTT message");
        }
    }
}
