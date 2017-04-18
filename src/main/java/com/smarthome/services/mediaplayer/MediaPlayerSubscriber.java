package com.smarthome.services.mediaplayer;

import com.smarthome.services.service.mqtt.MQTTServiceImpl;
import com.smarthome.services.service.mqtt.MQTTServiceCallback;

/**
 * Created by Ian C on 07/04/2017.
 */
public class MediaPlayerSubscriber extends MQTTServiceCallback {

    public MediaPlayerSubscriber(MQTTServiceImpl mqttServiceImpl) {
        super(mqttServiceImpl);
    }
}
