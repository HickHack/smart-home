package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @date 30/03/17
 */
public interface Service extends Runnable {

    /**
     * Initially start a service
     */
    void stop();

    /**
     * Shutdown and clean up a service.
     */
    void start();

    /**
     * Output a message to a services UI
     * @param message
     */
    void updateUIOutput(String message);

    /**
     * Update the top panel of a service UI
     * with current model state
     */
    void updateUIStatus();

    /**
     * Set the service controller implementation on
     * a service
     * @param controller
     */
    void setController(ServiceController controller);

    ServiceController getController();

    String getName();

    ServiceType getType();
}
