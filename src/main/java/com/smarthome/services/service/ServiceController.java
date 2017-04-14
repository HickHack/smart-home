package com.smarthome.services.service;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.List;
import java.util.Map;

/**
 * @author Graham Murray
 * @descripion Interface for service controllers
 */
public interface ServiceController {
    ServiceResponse performOperation(ServiceOperation request);
    Map getControllerStatus();
}
