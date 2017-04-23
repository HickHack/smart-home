package com.smarthome.services.service;

import java.util.Map;

/**
 * @author Graham Murray
 * @descripion Interface for service controllers
 */
public interface ServiceController {

    /**
     * Perform an action on a service based on
     * the request values set by the invoking service
     *
     * @param request
     * @return response
     */
    ServiceResponse performOperation(ServiceOperation request);

    /**
     *
     * @return Map containing model values
     */
    Map getControllerStatus();
}
