package com.smarthome.services.service;

/**
 * Created by graham on 30/03/17.
 */
public interface Service extends Runnable {
    void stop();
    void start();
    String getName();
}
