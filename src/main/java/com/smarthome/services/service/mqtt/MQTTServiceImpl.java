package com.smarthome.services.service.mqtt;

import com.google.gson.Gson;
import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceController;
import com.smarthome.services.service.ServiceResponse;
import com.smarthome.services.service.ServiceType;
import com.smarthome.ui.ServiceUI;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.smarthome.services.service.config.Config.BROKER;
import static com.smarthome.services.service.config.Config.PERSISTENCE;
import static com.smarthome.services.service.config.Config.QOS;

/**
 * @author Ian Cunningham
 */
public class MQTTServiceImpl implements MQTTService {

    private static String clientId = "MediaPlayer";
    private ServiceController serviceController;
    private MQTTSubscriber mediaPlayerSubscriber;
    private ServiceUI ui;
    private Gson gson;

    public MQTTServiceImpl(ServiceController serviceController) {
        this.serviceController = serviceController;
        gson = new Gson();
        ui = new ServiceUI(this);
        mediaPlayerSubscriber = new MQTTSubscriber(this);
    }

    public ServiceController getController() {
        return serviceController;
    }

    public void publish(ServiceResponse response) {
        try {
            MqttClient client = new MqttClient(BROKER, clientId, PERSISTENCE);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
            String json = gson.toJson(response);
            MqttMessage message = new MqttMessage(json.getBytes());
            message.setQos(QOS);
            client.publish(ServiceType.MEDIA_PLAYER.toString(), message);
            client.disconnect();
        } catch (MqttException me) {
            // TODO Update UI
        }
    }

    public void subscribe() {
        try {
            MqttClient sampleClient = new MqttClient(BROKER, clientId, PERSISTENCE);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.setCallback(mediaPlayerSubscriber);
            System.out.println("Connecting to BROKER: " + BROKER);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            sampleClient.subscribe("/smart_home/#");
        } catch (MqttException ex) {
            // TODO Update UI
        }
    }

    @Override
    public void run() {
        start();
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {
        subscribe();
    }

    @Override
    public void updateUIOutput(String message) {

    }

    @Override
    public void updateUIStatus() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ServiceType getType() {
        return null;
    }
}
