package com.smarthome.services;

import com.smarthome.services.service.ServiceOperation;
import com.smarthome.services.service.ServiceRequest;

/**
 * @author Graham Murray
 * @descripion Utility class for testing running services.
 *
 */
public class Tester {

    public static void main(String[] args) {
        ServiceRequest request = new ServiceRequest("Jacuzzi_Service", new ServiceOperation(0));
        request.send();

        if (request.isSuccessful()) {
            System.out.println("Response: " + request.getResponse());
        } else {
            System.out.println("Test Failed");
        }
    }
}
