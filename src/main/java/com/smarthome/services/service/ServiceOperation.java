package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @description Service operation model used to hold operation
 * codes that are used when invoking services
 */
public class ServiceOperation {

    private int operationCode;
    private String requester;

    public ServiceOperation(int operationCode) {
        this.operationCode = operationCode;
    }

    public int getOperationCode() {
        return operationCode;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getRequester() {
        return requester;
    }
}
