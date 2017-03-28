package com.smarthome.services.model;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service controller
 */
public class JacuzziModel extends BaseModel {

    private static int servicePort = 9090;
    private static long UUID = 1;
    private static String serviceName = "Jacuzzi_Service";
    private int waterDepth;
    private boolean isWaterRunning;
    private boolean isJetsRunning;
    private int jetPower;

    public JacuzziModel() {
        super(servicePort, UUID, serviceName);
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
