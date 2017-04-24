package com.smarthome.services.service.mqtt;

import com.google.gson.Gson;
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
    private Gson gson;
    private MqttClient client;
    private MqttCallback callback;

    public MQTTOperations(MQTTService service, MqttCallback callback) throws MqttException {
        this.service = service;
        this.callback = callback;
        gson = new Gson();

        configureClient();
    }

    /**
     * Close connection to the broker
     */
    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * Publish a message to the specified topic
     *
     * @param message
     * @param topic
     * @throws MqttException
     */
    public void publish(Object message, ServiceType topic) throws MqttException {
        String json = gson.toJson(message);
        MqttMessage mqttMessage = new MqttMessage(json.getBytes());
        mqttMessage.setQos(QOS);
        service.updateUIOutput("Publishing message to " + topic);
        client.publish(topic.toString(), mqttMessage);
    }

    /**
     * Subscribe to a topic. This can be called
     * multiple times
     *
     * @param topic
     * @throws MqttException
     */
    public void subscribe(ServiceType topic) throws MqttException {
        service.updateUIOutput("Connected to broker: " + BROKER);
        service.updateUIOutput("Subscribing to " + topic);
        client.subscribe(topic.toString());
    }

    /**
     * Connect to the broker a set the callback
     *
     * @throws MqttException
     */
    private void configureClient() throws MqttException {
        client = new MqttClient(BROKER, service.getName(), PERSISTENCE);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect(connOpts);
        client.setCallback(callback);
    }
}
