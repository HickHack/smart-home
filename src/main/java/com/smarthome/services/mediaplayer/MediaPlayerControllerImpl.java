package com.smarthome.services.mediaplayer;

import com.smarthome.services.mediaplayer.model.MediaplayerModel;
import com.smarthome.services.mediaplayer.model.PlaylistModel;
import com.smarthome.services.service.*;

/**
 * Created by Ian C on 06/04/2017.
 */
public class MediaPlayerControllerImpl implements ServiceController {

    private MediaplayerModel mpModel;
    private int volumeLevel;

    public MediaPlayerControllerImpl() {
        mpModel = new MediaplayerModel("");
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
        return new ServiceResponse(mpModel);
    }

    private void turnMediaPlayerOn() {
        if (!mpModel.isMediaplayerOn()) {
            mpModel.setMediaplayerOn(true);
            mpModel.setMuteOn(false);
            mpModel.setVolume(50);
        }
    }

    private void turnMediaPlayerOff() {
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

    private void increaseVolume() {
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

        mpModel.setTrack(mpModel.getTrack() + 1);
    }

    private void randomTrack() {
        if (mpModel.isMediaplayerOn()) {
            PlaylistModel playlist = new PlaylistModel();
            mpModel.setTrack(playlist.selectRandomTrack());
        }
    }
}
