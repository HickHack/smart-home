package com.smarthome.services.mediaplayer;

import com.google.gson.Gson;
import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceController;
import com.smarthome.services.service.ServiceOperation;
import com.smarthome.services.service.ServiceResponse;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Ian C on 07/04/2017.
 */
public class MediaPlayerSubscriber implements MqttCallback {

    private MediaPlayerServiceImpl mediaPlayerService;
    Gson gson;

    public MediaPlayerSubscriber(MediaPlayerServiceImpl mediaPlayerService) {
        gson = new Gson();
        this.mediaPlayerService = mediaPlayerService;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ServiceResponse serviceResponse = mediaPlayerService.getController().performOperation(gson.fromJson(mqttMessage.toString(), ServiceOperation.class));

        String serializedResponse = gson.toJson(serviceResponse);
        System.out.println("Media Player is sending: " + serializedResponse);
        mediaPlayerService.publishResponse(serializedResponse);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
