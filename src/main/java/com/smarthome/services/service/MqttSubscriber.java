package com.smarthome.services.service;

import com.google.gson.Gson;
import com.smarthome.services.mediaplayer.MediaPlayerServiceImpl;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Ian C on 16/04/2017.
 */
public class MqttSubscriber implements MqttCallback {

    private MqttServiceImpl service;
    Gson gson;

    public MqttSubscriber(MqttServiceImpl service) {
        gson = new Gson();
        this.service = service;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ServiceResponse serviceResponse = service.getController().performOperation(gson.fromJson(mqttMessage.toString(), ServiceOperation.class));

        String serializedResponse = gson.toJson(serviceResponse);
        System.out.println("Media Player is sending: " + serializedResponse);
        service.publishResponse(serializedResponse);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
