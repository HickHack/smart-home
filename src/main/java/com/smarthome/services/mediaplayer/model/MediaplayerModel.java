package com.smarthome.services.mediaplayer.model;

import com.smarthome.services.service.model.BaseServiceModel;

/**
 * Created by Ian C on 06/04/2017.
 */
public class MediaplayerModel extends BaseServiceModel {

    private static int PORT = 9093;
    private static String NAME = "Mediaplayer_Service";
    private boolean isMediaplayerOn;
    private boolean isMuteOn;
    private int volume;

    public MediaplayerModel() {
        super();

        setServicePort(PORT);
        setServiceName(NAME);
    }


    public boolean isMediaplayerOn() {
        return isMediaplayerOn;
    }

    public void setMediaplayerOn(boolean mediaplayerOn) {
        isMediaplayerOn = mediaplayerOn;
    }

    public boolean isMuteOn() {
        return isMuteOn;
    }

    public void setMuteOn(boolean muteOn) {
        isMuteOn = muteOn;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
