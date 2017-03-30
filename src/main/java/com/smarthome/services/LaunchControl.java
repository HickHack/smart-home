package com.smarthome.services;

import com.smarthome.services.jacuzzi.JacuzziServiceImpl;
import com.smarthome.services.lighting.LightingServiceImpl;
import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Utility class for testing running services.
 *
 */
public class LaunchControl {

    private DNSServiceDiscovery serviceDiscovery;

    private LaunchControl() throws InterruptedException {
        serviceDiscovery = new DNSServiceDiscovery();

        launchJacuzzi();
        launchLighting();

        Thread.sleep(10000);

        testJacuzziService();
        testLightingService();
    }

    private void launchJacuzzi() {
        Thread service = new Thread(new JacuzziServiceImpl("Jacuzzi_Service1", 9090));
        service.start();
    }

    private void launchLighting() {
        Thread service = new Thread(new LightingServiceImpl("LightingService1", 9091));
        service.start();
    }

    private void testJacuzziService() {
        serviceDiscovery.addServiceListener(ServiceType.JACUZZI);

        ServiceOperation operation = new ServiceOperation(0);
        ServiceRequest request = new ServiceRequest(serviceDiscovery.getServiceInfo(ServiceType.JACUZZI), operation);
        request.send();

        System.out.println("Response: " + request.getResponse());
    }

    private void testLightingService() {
        serviceDiscovery.addServiceListener(ServiceType.LIGHTING);

        ServiceOperation operation = new ServiceOperation(4);
        ServiceRequest request = new ServiceRequest(serviceDiscovery.getServiceInfo(ServiceType.LIGHTING), operation);
        request.send();

        System.out.println("Response: " + request.getResponse());
    }

    public static void main(String[] args) throws InterruptedException {
        new LaunchControl();
    }
}
