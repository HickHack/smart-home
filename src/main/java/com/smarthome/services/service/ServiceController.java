package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @descripion Interface for service controllers
 */
public interface ServiceController {
    ServiceResponse performOperation(ServiceOperation request);
}
