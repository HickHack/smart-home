package com.smarthome.services.mediaplayer;

import com.smarthome.services.service.*;
import com.smarthome.services.service.mqtt.MQTTServiceImpl;

/**
 * @author Ian Cunningham
 */
public class MediaPlayerServiceImpl extends MQTTServiceImpl {

    public MediaPlayerServiceImpl(String name) {
        super(name, ServiceType.MEDIA_PLAYER);
        setController(new MediaPlayerControllerImpl(this));
    }
}
