package com.smarthome.services;

import com.smarthome.services.jacuzzi.JacuzziServiceImpl;
import com.smarthome.services.lighting.LightingServiceImpl;
import com.smarthome.services.service.*;
import com.smarthome.services.television.TelevisionServiceImpl;

/**
 * @author Graham Murray
 * @descripion Utility class for testing running services.
 *
 */
public class LaunchControl {

    private DNSServiceDiscovery serviceDiscovery;
    private Thread jacuzziProcess;
    private Thread televisionProcess;
    private Thread lightingProcess;

    private LaunchControl() throws InterruptedException {
        serviceDiscovery = new DNSServiceDiscovery();

        launchJacuzzi();
        launchLighting();
        launchTelevision();
        addSubscribers();

        testJacuzziService();
        testLightingService();
        testTelevisionService();

        shutdown();
    }

    private void launchJacuzzi() throws InterruptedException {
        jacuzziProcess = new Thread(new JacuzziServiceImpl("Jacuzzi_Service1", 9090));
        jacuzziProcess.start();

        Thread.sleep(9000);
    }

    private void launchLighting() throws InterruptedException {
        lightingProcess = new Thread(new LightingServiceImpl("LightingService1", 9091));
        lightingProcess.start();

        Thread.sleep(9000);
    }

    private void launchTelevision() throws InterruptedException {
        televisionProcess = new Thread(new TelevisionServiceImpl("TelevisionService1", 9092));
        televisionProcess.start();

        Thread.sleep(9000);
    }

    private void testJacuzziService() {

        ServiceOperation operation = new ServiceOperation(0);
        ServiceRequest request = new ServiceRequest(serviceDiscovery.getServiceInfo(ServiceType.JACUZZI), operation);
        request.send();

        System.out.println("Response: " + request.getResponse());
    }

    private void testLightingService() {

        ServiceOperation operation = new ServiceOperation(4);
        ServiceRequest request = new ServiceRequest(serviceDiscovery.getServiceInfo(ServiceType.LIGHTING), operation);
        request.send();

        System.out.println("Response: " + request.getResponse());
    }

    private void testTelevisionService() {

        ServiceOperation operation = new ServiceOperation(0);
        ServiceRequest request = new ServiceRequest(serviceDiscovery.getServiceInfo(ServiceType.TELEVISION), operation);
        request.send();

        System.out.println("Response: " + request.getResponse());
    }

    public void shutdown() {
        jacuzziProcess.interrupt();
        lightingProcess.interrupt();
        televisionProcess.interrupt();
    }

    private void addSubscribers() {
        serviceDiscovery.addServiceListener(ServiceType.JACUZZI);
        serviceDiscovery.addServiceListener(ServiceType.LIGHTING);
        serviceDiscovery.addServiceListener(ServiceType.TELEVISION);
    }

    public static void main(String[] args) throws InterruptedException {
        new LaunchControl();
    }
}
