package com.smarthome.services.service;

/**
 * @author Graham Murray.
 * @description Helper class primarily for helping
 * controllers decide if the type of response they receive
 * corresponds to the expected result.
 */
public class ServiceHelper {

    public static boolean isValidResponse(Object object, Class clazz) {
        if (object != null && clazz.isInstance(object)) {
            return true;
        }

        return false;
    }
}
