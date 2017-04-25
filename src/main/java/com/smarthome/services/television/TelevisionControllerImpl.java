package com.smarthome.services.television;

import com.smarthome.services.mediaplayer.model.MediaPlayerModel;
import com.smarthome.services.service.*;
import com.smarthome.services.television.model.TelevisionModel;

import java.util.*;

import static com.smarthome.services.service.ServiceResponse.Status;

/**
 * @author Ian Cunningham
 */
public class TelevisionControllerImpl implements ServiceController {

    private TelevisionModel model;
    private TelevisionHybridService service;

    public TelevisionControllerImpl(TelevisionHybridService service) {
        model = new TelevisionModel(service.getName(), service.getPort());
        this.service = service;
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation request) {
        Status status;

        switch (request.getOperationCode()) {
            case 0:
                status = turnTelevisionOn();
                break;
            case 1:
                status = turnTelevisionOff();
                break;
            case 4:
                status = decreaseVolume();
                break;
            case 5:
                status = increaseVolume();
                break;
            default:
                status = Status.UNSUPPORTED_OPERATION;
                break;

        }

        return new ServiceResponse(status, model, service.getType());
    }

    @Override
    public Map getControllerStatus() {
        return model.getValuesMap();
    }

    public void pickSong(ServiceResponse response) {
        if (ServiceHelper.isValidResponse(response)) {
            MediaPlayerModel model = (MediaPlayerModel) response.getModel();

            if (!model.isTrackPlaying() && model.isMediaPlayerOn()) {
                List<Integer> tracks = model.getPlaylist().getTracks();

                Random random = new Random();
                int track = tracks.get(4) + random.nextInt(tracks.size());
                service.updateUIOutput("Selecting track " + track);
                service.publish(new ServiceOperation(track));
            }
         }
    }

    private Status turnTelevisionOn() {
        if (!model.isTelevisionOn()) {
            model.setTelevisionOn(true);
            model.setVolume(50);
            model.setScreenBrightness(60);
            service.updateUIOutput("Turning TV On. Volume: " + model.getVolume());
            service.updateUIOutput("Activating Media Player");
            service.publish(new ServiceOperation(0));

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status turnTelevisionOff() {
        if (model.isTelevisionOn()) {
            model.setTelevisionOn(false);
            model.setVolume(0);
            model.setScreenBrightness(0);
            service.updateUIOutput("Turning TV Off. Volume: " + model.getVolume());
            service.updateUIOutput("Turning Off Media Player");
            service.publish(new ServiceOperation(1));

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status decreaseVolume() {
        if (model.isTelevisionOn() && model.getVolume() > 0) {

            model.setVolume(model.getVolume() - 4);
            service.updateUIOutput("Decreasing volume. Volume: " + model.getVolume());

            return Status.OK;
        }

        model.setVolume(0);
        model.setMuteOn(true);
        service.updateUIOutput("Cant decrease volume. Volume: " + model.getVolume());
        return Status.FAILED;
    }

    private Status increaseVolume() {
        if (model.isTelevisionOn() && model.getVolume() < 100) {

            model.setMuteOn(false);

            model.setVolume(model.getVolume() + 4);
            service.updateUIOutput("Increasing volume. Volume" + model.getVolume());

            return Status.OK;
        }

        model.setVolume(100);
        service.updateUIOutput("Cant increase volume. Volume: " + model.getVolume());
        return Status.FAILED;
    }
}
