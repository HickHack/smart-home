package com.smarthome.services.mediaplayer;

import com.smarthome.services.service.*;
import com.smarthome.services.service.mqtt.MQTTServiceImpl;

/**
 * @author Ian Cunningham
 */
public class MediaPlayerServiceImpl extends MQTTServiceImpl {

    public MediaPlayerServiceImpl(ServiceController controller) {
        super(controller);
    }
}
