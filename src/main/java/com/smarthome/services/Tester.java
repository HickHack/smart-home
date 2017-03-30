package com.smarthome.services;

import com.smarthome.services.service.DNSServiceDiscovery;
import com.smarthome.services.service.ServiceOperation;
import com.smarthome.services.service.ServiceRequest;
import com.smarthome.services.service.ServiceType;

/**
 * @author Graham Murray
 * @descripion Utility class for testing running services.
 *
 */
public class Tester {

    public static void main(String[] args) {
        DNSServiceDiscovery serviceDiscovery = new DNSServiceDiscovery();
        serviceDiscovery.addServiceListener(ServiceType.JACUZZI);
        serviceDiscovery.addServiceListener(ServiceType.LIGHTING);
        ServiceOperation operation = new ServiceOperation(0);
        ServiceRequest request = new ServiceRequest(serviceDiscovery.getServiceInfo(ServiceType.JACUZZI), operation);
        request.send();

        operation = new ServiceOperation(4);
        ServiceRequest request2 = new ServiceRequest(serviceDiscovery.getServiceInfo(ServiceType.LIGHTING), operation);
        request2.send();

        if (request.isSuccessful()) {
            System.out.println("Response: " + request.getResponse());
            System.out.println("Response: " + request2.getResponse());
        } else {
            System.out.println("Test Failed");
        }
    }
}
