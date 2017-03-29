package com.smarthome.services.service;

import com.smarthome.services.service.Service;

/**
 * Created by graham on 29/03/17.
 */
public class ServiceOperation {

    private int operationCode;

    public ServiceOperation(int operationCode) {
        this.operationCode = operationCode;
    }

    public int getOperationCode() {
        return operationCode;
    }
}
