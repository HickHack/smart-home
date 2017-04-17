package com.smarthome.services.service;

/**
 * @author Graham Murray.
 * @description Helper class primarily for helping
 * controllers decide if the type of response they receive
 * corresponds to the expected result.
 */
public class ServiceHelper {

    public static boolean isValidResponse(ServiceResponse response) {
        if (response != null && response.getStatus().equals(ServiceResponse.Status.OK)) {
            return true;
        }

        return false;
    }

    public static boolean isValidResponse(ServiceResponse response, Class expected) {
        if (response != null &&
                expected.isInstance(response.getModel()) &&
                response.getStatus().equals(ServiceResponse.Status.OK)) {

            return true;
        }

        return false;
    }
}
