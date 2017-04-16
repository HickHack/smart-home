package com.smarthome.services.service;

import com.smarthome.services.mediaplayer.MediaPlayerControllerImpl;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.smarthome.services.service.config.Config.BROKER;
import static com.smarthome.services.service.config.Config.PERSISTENCE;
import static com.smarthome.services.service.config.Config.QOS;

/**
 * Created by Ian C on 16/04/2017.
 */
public class MqttServiceImpl implements Runnable{

    private String clientId = "Publisher_Id";
    private MqttSubscriber mqttSubscriber;
    private ServiceController serviceController;

    public MqttServiceImpl() {
        mqttSubscriber = new MqttSubscriber(this);
        serviceController = new MediaPlayerControllerImpl();
    }

    public ServiceController getController() {
        return serviceController;
    }

    public void publishResponse(String serializedServiceResponse) {
        try {
            MqttClient responseClient = new MqttClient(BROKER, clientId, PERSISTENCE);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to BROKER: " + BROKER);
            responseClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + serializedServiceResponse);
            MqttMessage message = new MqttMessage(serializedServiceResponse.getBytes());
            message.setQos(QOS);
            responseClient.publish(ServiceType.MEDIA_PLAYER.toString(), message);
            System.out.println("Message published");
            responseClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            mqttEx(me);
        }
    }


    public void subscribe(String clientId, MqttSubscriber mqttSubscriber) {
        try {
            MqttClient sampleClient = new MqttClient(BROKER, clientId, PERSISTENCE);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.setCallback(mqttSubscriber);
            System.out.println("Connecting to BROKER: " + BROKER);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            sampleClient.subscribe("/smart_home/#");
        } catch (MqttException me) {
            mqttEx(me);
        }
    }

    public void mqttEx(MqttException me) {
        System.out.println("reason " + me.getReasonCode() + "\nmsg " + me.getMessage() + "\nloc " + me.getLocalizedMessage() + "\ncause " + me.getCause() + "\nexcep " + me);
        me.printStackTrace();
    }

    @Override
    public void run() {
        subscribe(clientId, mqttSubscriber);
    }
}
