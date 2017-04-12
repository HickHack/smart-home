package com.smarthome.services.lighting.model;

import com.smarthome.services.service.model.BaseServiceModel;

/**
 * @author Graham Murray
 * @descripion Lighting service controller
 */
public class LightingModel extends BaseServiceModel {

    private boolean isLightingOn;
    private int brightness;

    public LightingModel(String name, int port) {
        super(name, port);
        isLightingOn = false;
        brightness = 0;
    }


    public void setLightingOn(boolean lightingOn) {
        isLightingOn = lightingOn;
    }

    public boolean isLightingOn() {
        return isLightingOn;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getBrightness() {
        return brightness;
    }
}
