package com.smarthome.services.jacuzzi.model;

import com.smarthome.services.service.model.BaseServiceModel;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service controller
 */
public class JacuzziModel extends BaseServiceModel {

    private int waterDepth;
    private boolean isWaterRunning;
    private boolean isJetsRunning;
    private int jetPower;

    public JacuzziModel(String name, int port) {
        super(name, port);
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
