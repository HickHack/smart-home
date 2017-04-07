package com.smarthome.services.mediaplayer;

import com.smarthome.services.mediaplayer.model.MediaplayerModel;
import com.smarthome.services.mediaplayer.model.Playlist;
import com.smarthome.services.service.*;
import com.smarthome.services.television.model.TelevisionModel;

/**
 * Created by Ian C on 06/04/2017.
 */
public class MediaplayerControllerImpl implements ServiceController {

    private MediaplayerModel mpModel;
    private Service service;
    private int volumeLevel;

    public MediaplayerControllerImpl(TCPService service) {
        mpModel = new MediaplayerModel();
        this.service = service;
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation request) {

        switch (request.getOperationCode()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                break;

        }
        return new ServiceResponse(mpModel);
    }

    private void turnMediaplayerOn() {
        if (!mpModel.isMediaplayerOn()) {
            mpModel.setMediaplayerOn(true);
            mpModel.setMuteOn(false);
            mpModel.setVolume(50);
        }
    }

    private void turnMediaplayerOff() {
        if (mpModel.isMediaplayerOn()) {
            mpModel.setMediaplayerOn(false);
            mpModel.setVolume(0);
        }
    }

    private void turnMuteOn() {
        if (mpModel.isMediaplayerOn() && !mpModel.isMuteOn()) {
            mpModel.setMuteOn(true);
            volumeLevel = mpModel.getVolume();
            mpModel.setVolume(0);
        }
    }

    private void turnMuteOff() {
        if (mpModel.isMediaplayerOn() && mpModel.isMuteOn()) {
            mpModel.setMuteOn(false);
            mpModel.setVolume(volumeLevel);
        }
    }

    private void decreaseVolume() {
        if (mpModel.isMediaplayerOn() && mpModel.getVolume() > 0) {

            if (mpModel.getVolume() - 1 == 0) {
                mpModel.setVolume(0);
            }

            mpModel.setVolume(mpModel.getVolume() - 1);
        }

    }

    private void increaseVolumne() {
        if (mpModel.isMediaplayerOn() && mpModel.getVolume() < 100) {

            if (mpModel.getVolume() + 1 == 100) {
                mpModel.setVolume(100);
            }

            mpModel.setVolume(mpModel.getVolume() + 1);
        }
    }

    private void previousTrack() {
        if (mpModel.isMediaplayerOn() && mpModel.getTrack() > 0) {
            if (mpModel.getTrack() - 1 == 0) {
                mpModel.setTrack(20);
                mpModel.setVolume(50);
            }
        }

        mpModel.setTrack(mpModel.getTrack() - 1);
    }

    private void nextTrack() {
        if (mpModel.isMediaplayerOn() && mpModel.getTrack() <= 20) {
            if (mpModel.getTrack() + 1 > 20) {
                mpModel.setTrack(1);
                mpModel.setVolume(50);
            }
        }

        mpModel.setTrack(mpModel.getTrack() - 1);
    }

    private void randomTrack() {
        if (mpModel.isMediaplayerOn()) {
            Playlist playlist = new Playlist();
            mpModel.setTrack(playlist.selectRandomTrack());
        }
    }
}
