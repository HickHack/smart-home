package com.smarthome.services.service;

/**
 * @author Graham Murray
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
