package com.smarthome.services.lighting.model;

import com.smarthome.services.service.model.BaseServiceModel;

/**
 * @author Graham Murray
 * @descripion Lighting service controller
 */
public class LightingModel extends BaseServiceModel {

    public static int PORT = 9091;
    private static String NAME = "Lighting_Service";
    private boolean isLightingOn;
    private int brightness;

    public LightingModel() {
        super();
        isLightingOn = false;
        brightness = 0;

        setServicePort(PORT);
        setServiceName(NAME);
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
