package com.smarthome.services.service;

import com.smarthome.services.service.model.BaseServiceModel;

/**
 * Created by graham on 28/03/17.
 */
public class ServiceResponse {

    private BaseServiceModel status;

    public ServiceResponse(BaseServiceModel status) {
        this.status = status;
    }

    public BaseServiceModel getStatus() {
        return status;
    }
}
