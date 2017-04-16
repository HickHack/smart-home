package com.smarthome.services.mediaplayer;

import com.google.gson.Gson;
import com.smarthome.services.service.*;
import com.smarthome.services.service.model.BaseServiceModel;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Ian C on 07/04/2017.
 */
public class MediaPlayerSubscriber extends MqttSubscriber {

    public MediaPlayerSubscriber(MediaPlayerServiceImpl mediaPlayerService) {
        super(mediaPlayerService);
    }
    
}
