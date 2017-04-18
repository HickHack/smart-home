package com.smarthome.services.service.mqtt;

import com.google.gson.Gson;
import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceController;
import com.smarthome.services.service.ServiceResponse;
import com.smarthome.services.service.ServiceType;
import com.smarthome.ui.ServiceUI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Ian Cunningham
 */
public class MQTTServiceImpl implements MQTTService, Service {

    private ServiceController controller;
    private MQTTOperations operations;
    private ServiceUI ui;
    private Gson gson;
    private String name;
    private ServiceType serviceType;

    public MQTTServiceImpl(String name, ServiceType serviceType) {
        this.name = name;
        this.serviceType = serviceType;
        gson = new Gson();
        ui = new ServiceUI(this);
        operations = new MQTTOperations(this);
    }

    @Override
    public ServiceController getController() {
        return controller;
    }

    @Override
    public void publish(ServiceResponse response) {
        try {
            String json = gson.toJson(response);
            operations.publish(json, this.getType());
        } catch (MqttException me) {
            updateUIOutput("Publishing response.");
        }
    }

    @Override
    public void subscribe() {
        try {
            operations.subscribe(ServiceType.MEDIA_PLAYER);
        } catch (MqttException ex) {
            updateUIOutput("Subscribing to topic  " + ServiceType.MEDIA_PLAYER);
        }
    }

    @Override
    public void run() {
        start();
    }

    @Override
    public void stop() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void start() {
        subscribe();
    }

    @Override
    public void updateUIOutput(String message) {
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
