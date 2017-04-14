package com.smarthome.services.mediaplayer;

import com.smarthome.services.service.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.smarthome.services.service.config.Config.*;

/**
 * Created by Ian C on 06/04/2017.
 */
public class MediaPlayerServiceImpl implements Runnable {

    private static String clientId = "MediaPlayer";
    private ServiceController serviceController;
    private MediaPlayerSubscriber mediaPlayerSubscriber;

    public MediaPlayerServiceImpl() {
        serviceController = new MediaPlayerControllerImpl();
        mediaPlayerSubscriber = new MediaPlayerSubscriber(this);
    }

    public ServiceController getController() {
        return serviceController;
    }

    public void publishResponse(String serializedServiceResponse) {
        try {
            MqttClient sampleClient = new MqttClient(BROKER, clientId, PERSISTENCE);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to BROKER: " + BROKER);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + serializedServiceResponse);
            MqttMessage message = new MqttMessage(serializedServiceResponse.getBytes());
            message.setQos(QOS);
            sampleClient.publish(ServiceType.MEDIA_PLAYER.toString(), message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
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
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    @Override
    public void run() {
        subscribe();
    }
}
