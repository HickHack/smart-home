package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @descripion Jacuzzi request received listener. Used to notify controller
 * that a message has been received so it can process it so the server can process
 * a response
 */
public interface ServiceControllerListener {
    String processRequest();
}
