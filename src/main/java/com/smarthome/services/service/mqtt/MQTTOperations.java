package com.smarthome.services.service.mqtt;

import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceType;
import org.eclipse.paho.client.mqttv3.*;

import static com.smarthome.services.service.config.Config.BROKER;
import static com.smarthome.services.service.config.Config.PERSISTENCE;
import static com.smarthome.services.service.config.Config.QOS;

/**
 * @author Graham Murray
 */
public class MQTTOperations {

    private Service service;
    private MQTTServiceCallback callback;

    public MQTTOperations(MQTTService service) {
        this.service = service;
        callback = new MQTTServiceCallback(service);
    }

    public void publish(String response, ServiceType topic) throws MqttException {
        MqttClient client = new MqttClient(BROKER, service.getName(), PERSISTENCE);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect(connOpts);
        MqttMessage message = new MqttMessage(response.getBytes());
        message.setQos(QOS);
        client.publish(topic.toString(), message);
        client.disconnect();
    }

    public void subscribe(ServiceType topic) throws MqttException {
        MqttClient client = new MqttClient(BROKER, service.getName(), PERSISTENCE);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.setCallback(callback);
        client.connect(connOpts);
        client.subscribe(topic.toString());
    }
}
