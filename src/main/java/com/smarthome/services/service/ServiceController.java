package com.smarthome.services.service;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.Map;

/**
 * @author Graham Murray
 * @descripion Interface for service controllers
 */
public interface ServiceController {
    BaseServiceModel performOperation(ServiceOperation request);
    Map getModelStatus();
}
