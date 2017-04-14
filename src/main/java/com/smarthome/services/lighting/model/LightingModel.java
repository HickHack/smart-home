package com.smarthome.services.lighting.model;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Map getValuesMap() {
        Map valuesMap = new HashMap();
        valuesMap.put("Lighting On", isLightingOn);
        valuesMap.put("Brightness", brightness);

        return valuesMap;
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
