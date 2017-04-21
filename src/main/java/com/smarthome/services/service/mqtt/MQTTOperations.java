package com.smarthome.services.service.mqtt;

import com.google.gson.Gson;
import com.smarthome.services.service.Service;
import com.smarthome.services.service.tcp.ServiceType;
import com.smarthome.services.television.TelevisionMQTTCallback;
import org.eclipse.paho.client.mqttv3.*;

import static com.smarthome.services.service.config.Config.BROKER;
import static com.smarthome.services.service.config.Config.PERSISTENCE;
import static com.smarthome.services.service.config.Config.QOS;

/**
 * @author Graham Murray
 */
public class MQTTOperations {

    private Service service;
    private Gson gson;
    private MqttClient client;
    private MqttCallback callback;

    public MQTTOperations(MQTTService service, MqttCallback callback) throws MqttException {
        this.service = service;
        this.callback = callback;
        gson = new Gson();

        configureClient();
    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(Object message, ServiceType topic) throws MqttException {
        String json = gson.toJson(message);
        MqttMessage mqttMessage = new MqttMessage(json.getBytes());
        mqttMessage.setQos(QOS);
        service.updateUIOutput("Publishing message to " + topic);
        client.publish(topic.toString(), mqttMessage);
    }

    public void subscribe(ServiceType topic) throws MqttException {
        service.updateUIOutput("Subscribing to " + topic);
        client.subscribe(topic.toString());
    }

    private void configureClient() throws MqttException {
        client = new MqttClient(BROKER, service.getName(), PERSISTENCE);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect(connOpts);
        client.setCallback(callback);
    }
}
