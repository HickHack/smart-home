package com.smarthome.services.mediaplayer;

import com.smarthome.services.mediaplayer.model.MediaPlayerModel;
import com.smarthome.services.mediaplayer.model.PlaylistModel;
import com.smarthome.services.service.*;
import com.smarthome.services.service.mqtt.MQTTService;
import com.smarthome.services.service.ServiceType;

import java.util.Map;


import static com.smarthome.services.service.ServiceResponse.Status;
/**
 * @author Ian Cunningham
 */
public class MediaPlayerControllerImpl implements ServiceController {

    private MQTTService service;
    private MediaPlayerModel model;
    private int volumeLevel;

    public MediaPlayerControllerImpl(MQTTService service) {
        this.service = service;
        model = new MediaPlayerModel(service.getName());
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
                status = turnMuteOn();
                break;
            case 3:
                status = turnMuteOff();
                break;
            case 4:
                status = decreaseVolume();
                break;
            case 5:
                status = increaseVolume();
                break;
            case 6:
                status = previousTrack();
                break;
            case 7:
                status = nextTrack();
                break;
            case 8:
                status = randomTrack();
                break;
            default:
                status = Status.UNSUPPORTED_OPERATION;
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
            model.setMuteOn(false);
            model.setVolume(50);

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status turnMediaPlayerOff() {
        if (model.isMediaPlayerOn()) {
            model.setMediaPlayerOn(false);
            model.setVolume(0);

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status turnMuteOn() {
        if (model.isMediaPlayerOn() && !model.isMuteOn()) {
            model.setMuteOn(true);
            volumeLevel = model.getVolume();
            model.setVolume(0);

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status turnMuteOff() {
        if (model.isMediaPlayerOn() && model.isMuteOn()) {
            model.setMuteOn(false);
            model.setVolume(volumeLevel);

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

    private Status previousTrack() {
        if (model.isMediaPlayerOn() && model.getTrack() > 0) {
            if (model.getTrack() - 1 == 0) {
                model.setTrack(20);
                model.setVolume(50);
            }
        }

        model.setTrack(model.getTrack() - 1);

        return Status.OK;
    }

    private Status nextTrack() {
        if (model.isMediaPlayerOn() && model.getTrack() <= 20) {
            if (model.getTrack() + 1 > 20) {
                model.setTrack(1);
                model.setVolume(50);
            }
        }

        model.setTrack(model.getTrack() + 1);

        return Status.OK;
    }

    private Status randomTrack() {
        if (model.isMediaPlayerOn()) {
            PlaylistModel playlist = new PlaylistModel();
            model.setTrack(playlist.selectRandomTrack());
        }

        return Status.OK;
    }


}
