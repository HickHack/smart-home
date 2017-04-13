package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @date 30/03/17
 */
public interface Service extends Runnable {
    void stop();
    void start();
    void updateUI(String message);
    String getName();
    ServiceType getType();
    int getPort();
}
