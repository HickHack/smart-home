package com.smarthome.services.service;

/**
 * @author Graham Murray.
 * @description Helper class primarily for helping
 * controllers.
 */
public class ServiceHelper {

    /**
     * Check a service response and check if the operation
     * performed was successful
     *
     * @param response
     * @return true if valid false if not.
     */
    public static boolean isValidResponse(ServiceResponse response) {
        return response != null && response.getStatus().equals(ServiceResponse.Status.OK);
    }
}
