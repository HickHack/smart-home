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
public class MediaPlayerServiceImpl extends MqttServiceImpl {

    private static String clientId = "MediaPlayer";
   // private ServiceController serviceController;
    private MqttSubscriber mediaPlayerSubscriber;

    public MediaPlayerServiceImpl() {
        super();
       // serviceController = new MediaPlayerControllerImpl();
        mediaPlayerSubscriber = new MediaPlayerSubscriber(this);
    }

    @Override
    public void run() {
        subscribe(clientId, mediaPlayerSubscriber);
    }
}
