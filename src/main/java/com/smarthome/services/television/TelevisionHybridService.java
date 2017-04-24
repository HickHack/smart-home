package com.smarthome.services.television;

import com.smarthome.services.service.mqtt.MQTTOperations;
import com.smarthome.services.service.mqtt.MQTTService;
import com.smarthome.services.service.ServiceType;
import com.smarthome.services.service.tcp.TCPServiceImpl;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Ian Cunningham
 */
public class TelevisionHybridService extends TCPServiceImpl implements MQTTService {

    private MQTTOperations mqttOperations;

    public TelevisionHybridService(String name) {
        super(name, ServiceType.TCP_TELEVISION);

        try {
            mqttOperations = new MQTTOperations(this, new TelevisionMQTTCallback(this));
        } catch (MqttException ex) {
            updateUIOutput("Failed to connect to MQTT");
        }
    }

    @Override
    public void run() {
        setController(new TelevisionControllerImpl(this));
        subscribe();
        start();
    }

    /**
     *
     * @param message
     *
     */
    @Override
    public void publish(Object message) {
        try {
            mqttOperations.publish(message, ServiceType.MQTT_MEDIA_PLAYER);
        } catch (MqttException e) {
            updateUIOutput("Failed to publish message");
        }
    }

    @Override
    public void subscribe() {
        try {
            mqttOperations.subscribe(ServiceType.MQTT_TELEVISION);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
