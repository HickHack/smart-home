package com.smarthome.services.jacuzzi.model;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Graham Murray
 * @descripion Jacuzzi model used to represent the current
 * state of the jacuzzi
 */
public class JacuzziModel extends BaseServiceModel {

    private int waterDepth;
    private boolean isWaterRunning;
    private boolean isJetsRunning;
    private int jetPower;

    public JacuzziModel(String name, int port) {
        super(name, port);
    }

    @Override
    public Map getValuesMap() {
        Map valuesMap = new HashMap();
        valuesMap.put("Water Depth", waterDepth);
        valuesMap.put("Water Running", isWaterRunning);
        valuesMap.put("Jets Running", isJetsRunning);
        valuesMap.put("Jet Power", jetPower);

        return valuesMap;
    }

    public void setWaterDepth(int waterDepth) {
        this.waterDepth = waterDepth;
    }

    public void setWaterRunning(boolean waterRunning) {
        isWaterRunning = waterRunning;
    }

    public int getWaterDepth() {
        return waterDepth;
    }

    public boolean isWaterRunning() {
        return isWaterRunning;
    }

    public void setJetsRunning(boolean jetsRunning) {
        isJetsRunning = jetsRunning;
    }

    public boolean isJetsRunning() {
        return isJetsRunning;
    }

    public void setJetPower(int jetPower) {
        this.jetPower = jetPower;
    }

    public int getJetPower() {
        return jetPower;
    }

}
