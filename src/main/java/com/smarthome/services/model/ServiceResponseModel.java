package com.smarthome.services.model;

/**
 * Created by graham on 28/03/17.
 */
public class ServiceResponseModel {

    private BaseModel status;

    public ServiceResponseModel(BaseModel status) {
        this.status = status;
    }

    public BaseModel getStatus() {
        return status;
    }
}
