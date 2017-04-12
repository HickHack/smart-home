package com.smarthome.services.television.model;

import com.smarthome.services.service.model.BaseServiceModel;

/**
 * Created by Ian C on 01/04/2017.
 */
public class TelevisionModel extends BaseServiceModel {

    private boolean isTelevisionOn;
    private boolean isMuteOn;
    private int volume;
    private int screenBrightness;

    public TelevisionModel(String name, int port) {
        super(name, port);
        isTelevisionOn = false;
        isMuteOn = false;
        volume = 0;
        screenBrightness = 0;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setScreenBrightness(int screenBrightness) {
        this.screenBrightness = screenBrightness;
    }

    public int getScreenBrightness() {
        return screenBrightness;
    }

    public void setTelevisionOn(boolean isTelevisionOn) {
        this.isTelevisionOn = isTelevisionOn;
    }

    public boolean isTelevisionOn() {
        return isTelevisionOn;
    }

    public void setMuteOn(boolean isMuteOn) {
        this.isMuteOn = isMuteOn;
    }

    public boolean isMuteOn() {
        return isMuteOn;
    }
}
