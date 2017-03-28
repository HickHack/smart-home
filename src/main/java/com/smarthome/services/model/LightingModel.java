package com.smarthome.services.model;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service controller
 */
public class LightingModel extends BaseModel {


    private boolean isLightingOn;
    private int brightness;

    public LightingModel() {
        super();
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
