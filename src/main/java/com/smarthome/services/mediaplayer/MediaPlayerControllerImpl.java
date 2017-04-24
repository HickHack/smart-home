package com.smarthome.services.mediaplayer;

import com.smarthome.services.mediaplayer.model.MediaPlayerModel;
import com.smarthome.services.service.*;
import com.smarthome.services.service.mqtt.MQTTService;
import com.smarthome.services.service.ServiceType;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import static com.smarthome.services.service.ServiceResponse.Status;
/**
 * @author Ian Cunningham
 */
public class MediaPlayerControllerImpl implements ServiceController {

    private MQTTService service;
    private MediaPlayerModel model;
    private Timer timer;

    public MediaPlayerControllerImpl(MQTTService service) {
        this.service = service;
        model = new MediaPlayerModel(service.getName());
        timer = new Timer();
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation request) {
        Status status;

        switch (request.getOperationCode()) {
            case 0:
                status = turnMediaPlayerOn();
                break;
            case 1:
                status = turnMediaPlayerOff();
                break;
            case 2:
                status = decreaseVolume();
                break;
            case 3:
                status = increaseVolume();
                break;
            default:
                status = selectTrack(request.getOperationCode());
                break;

        }

        return new ServiceResponse(status, model, ServiceType.MQTT_MEDIA_PLAYER);
    }

    @Override
    public Map getControllerStatus() {
        return model.getValuesMap();
    }

    private Status turnMediaPlayerOn() {
        if (!model.isMediaPlayerOn()) {
            model.setMediaPlayerOn(true);
            model.setVolume(50);

            service.updateUIOutput("Turning Media Player On");
            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status turnMediaPlayerOff() {
        if (model.isMediaPlayerOn()) {
            model.setMediaPlayerOn(false);
            model.setVolume(0);

            service.updateUIOutput("Turning Media Player Off");
            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status decreaseVolume() {
        if (model.isMediaPlayerOn() && model.getVolume() > 0) {

            if (model.getVolume() - 1 == 0) {
                model.setVolume(0);
            }

            model.setVolume(model.getVolume() - 1);

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status increaseVolume() {
        if (model.isMediaPlayerOn() && model.getVolume() < 100) {

            if (model.getVolume() + 1 == 100) {
                model.setVolume(100);
            }

            model.setVolume(model.getVolume() + 1);

            return Status.OK;
        }

        return Status.FAILED;
    }


    private Status selectTrack(int code) {
        if (model.isMediaPlayerOn()) {
            model.selectTrack(code);
            timer.schedule(new MediaPlayerTask(), 0, 10000);

            service.updateUIOutput("Selecting Track " + code);
        }

        return Status.OK;
    }

    class MediaPlayerTask extends TimerTask {

        @Override
        public void run() {
            service.updateUIStatus();
            service.updateUIOutput("Playing track " + model.getCurrentTrack());
            service.publish(new ServiceResponse(Status.OK, model, ServiceType.MQTT_MEDIA_PLAYER));
        }
    }


}
