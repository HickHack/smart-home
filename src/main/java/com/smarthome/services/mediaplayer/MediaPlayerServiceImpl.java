package com.smarthome.services.mediaplayer;

import com.smarthome.services.service.mqtt.MQTTServiceImpl;
import com.smarthome.services.service.tcp.ServiceType;

/**
 * @author Ian Cunningham
 */
public class MediaPlayerServiceImpl extends MQTTServiceImpl {

    public MediaPlayerServiceImpl(String name) {
        super(name, ServiceType.MQTT_MEDIA_PLAYER, new MediaPlayerMQTTCallback());
        setController(new MediaPlayerControllerImpl(this));
    }
}
