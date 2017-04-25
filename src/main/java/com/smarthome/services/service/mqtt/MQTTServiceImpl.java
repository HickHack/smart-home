package com.smarthome.services.service.mqtt;

import com.smarthome.services.mediaplayer.MediaPlayerMQTTCallback;
import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceController;
import com.smarthome.services.service.ServiceType;
import com.smarthome.ui.service.ServiceUI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Graham Murray
 * @descripion MQTT service implementation. See
 * the implemented interfaces for comments
 */
public class MQTTServiceImpl implements MQTTService, Service {

    private ServiceController controller;
    private MQTTOperations operations;
    private ServiceUI ui;
    private String name;
    private ServiceType serviceType;

    public MQTTServiceImpl(String name, ServiceType serviceType, MediaPlayerMQTTCallback callback) {
        this.name = name;
        this.serviceType = serviceType;
        ui = new ServiceUI(this);

        try {
            callback.setService(this);
            operations = new MQTTOperations(this, callback);
        } catch (MqttException ex) {
            updateUIOutput("Failed to connect to MQTT");
        }
    }

    @Override
    public ServiceController getController() {
        return controller;
    }

    @Override
    public void publish(Object message) {
        try {
            operations.publish(message, ServiceType.MQTT_TELEVISION);
        } catch (MqttException me) {
            updateUIOutput("Publishing response to " + this.getType().toString());
        }
    }

    @Override
    public void subscribe() {
        try {
            operations.subscribe(this.getType());
        } catch (MqttException ex) {
            updateUIOutput("Failed to subscribe to " + this.getType());
        }
    }

    @Override
    public void run() {
        start();
    }

    @Override
    public void stop() {
        operations.disconnect();
        Thread.currentThread().interrupt();
    }

    @Override
    public void start() {
        ui.init();
        updateUIOutput("Starting " + name);
        updateUIStatus();
        subscribe();
    }

    @Override
    public void updateUIOutput(String message) {
        updateUIStatus();
        ui.updateOutput(message);
    }

    @Override
    public void updateUIStatus() {
        ui.updateStatusAttributes(controller.getControllerStatus());
    }

    @Override
    public void setController(ServiceController controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ServiceType getType() {
        return serviceType;
    }
}
