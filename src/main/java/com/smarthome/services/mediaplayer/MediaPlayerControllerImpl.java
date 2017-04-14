package com.smarthome.services.mediaplayer;

import com.smarthome.services.mediaplayer.model.MediaPlayerModel;
import com.smarthome.services.mediaplayer.model.PlaylistModel;
import com.smarthome.services.service.*;
import com.smarthome.services.service.model.BaseServiceModel;

import java.util.Map;

/**
 * @author Ian Cunningham
 */
public class MediaPlayerControllerImpl implements ServiceController {

    private MediaPlayerModel mpModel;
    private int volumeLevel;

    public MediaPlayerControllerImpl() {
        mpModel = new MediaPlayerModel("");
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation request) {

        switch (request.getOperationCode()) {
            case 0:
                turnMediaPlayerOn();
                break;
            case 1:
                turnMediaPlayerOff();
                break;
            case 2:
                turnMuteOn();
                break;
            case 3:
                turnMuteOff();
                break;
            case 4:
                decreaseVolume();
                break;
            case 5:
                increaseVolume();
                break;
            case 6:
                previousTrack();
                break;
            case 7:
                nextTrack();
                break;
            case 8:
                randomTrack();
            default:
                break;

        }

        return new ServiceResponse(ServiceResponse.Status.OK, mpModel);
    }

    @Override
    public Map getControllerStatus() {
        return null;
    }

    private void turnMediaPlayerOn() {
        if (!mpModel.isMediaPlayerOn()) {
            mpModel.setMediaPlayerOn(true);
            mpModel.setMuteOn(false);
            mpModel.setVolume(50);
        }
    }

    private void turnMediaPlayerOff() {
        if (mpModel.isMediaPlayerOn()) {
            mpModel.setMediaPlayerOn(false);
            mpModel.setVolume(0);
        }
    }

    private void turnMuteOn() {
        if (mpModel.isMediaPlayerOn() && !mpModel.isMuteOn()) {
            mpModel.setMuteOn(true);
            volumeLevel = mpModel.getVolume();
            mpModel.setVolume(0);
        }
    }

    private void turnMuteOff() {
        if (mpModel.isMediaPlayerOn() && mpModel.isMuteOn()) {
            mpModel.setMuteOn(false);
            mpModel.setVolume(volumeLevel);
        }
    }

    private void decreaseVolume() {
        if (mpModel.isMediaPlayerOn() && mpModel.getVolume() > 0) {

            if (mpModel.getVolume() - 1 == 0) {
                mpModel.setVolume(0);
            }

            mpModel.setVolume(mpModel.getVolume() - 1);
        }

    }

    private void increaseVolume() {
        if (mpModel.isMediaPlayerOn() && mpModel.getVolume() < 100) {

            if (mpModel.getVolume() + 1 == 100) {
                mpModel.setVolume(100);
            }

            mpModel.setVolume(mpModel.getVolume() + 1);
        }
    }

    private void previousTrack() {
        if (mpModel.isMediaPlayerOn() && mpModel.getTrack() > 0) {
            if (mpModel.getTrack() - 1 == 0) {
                mpModel.setTrack(20);
                mpModel.setVolume(50);
            }
        }

        mpModel.setTrack(mpModel.getTrack() - 1);
    }

    private void nextTrack() {
        if (mpModel.isMediaPlayerOn() && mpModel.getTrack() <= 20) {
            if (mpModel.getTrack() + 1 > 20) {
                mpModel.setTrack(1);
                mpModel.setVolume(50);
            }
        }

        mpModel.setTrack(mpModel.getTrack() + 1);
    }

    private void randomTrack() {
        if (mpModel.isMediaPlayerOn()) {
            PlaylistModel playlist = new PlaylistModel();
            mpModel.setTrack(playlist.selectRandomTrack());
        }
    }
}
