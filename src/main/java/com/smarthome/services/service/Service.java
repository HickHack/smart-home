package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @date 30/03/17
 */
public interface Service extends Runnable {
    void stop();
    void start();
    void updateUIOutput(String message);
    void updateUIStatus();
    void setController(ServiceController controller);
    String getName();
    ServiceType getType();
}
