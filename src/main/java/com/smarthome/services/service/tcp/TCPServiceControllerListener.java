package com.smarthome.services.service.tcp;

/**
 * @author Graham Murray
 */
public interface TCPServiceControllerListener {

    /**
     * Request received listener. Used to notify a
     * TCP service that a message has been received
     * so it can process it so the TCPServiceServer
     * can process the response. The observer pattern
     * is used here to notify listeners who register
     * with a server. All listeners will be notified
     * when a request is received.
     *
     * @return a stringified json ServiceResponse.
     */
    String processRequest();
}
