package com.smarthome.services;

import com.smarthome.services.jacuzzi.JacuzziServiceImpl;
import com.smarthome.services.lighting.LightingServiceImpl;
import com.smarthome.services.mediaplayer.MediaPlayerServiceImpl;
import com.smarthome.services.service.*;
import com.smarthome.services.service.ServiceType;
import com.smarthome.services.service.tcp.discovery.DNSServiceDiscovery;
import com.smarthome.services.television.TelevisionHybridService;
import com.smarthome.ui.client.ClientUI;

import javax.jmdns.ServiceInfo;
import javax.swing.*;

/**
 * @author Graham Murray
 * @descripion Controller used by the client UI to launch and control
 * services
 *
 */
public class LaunchControl extends Thread{

    private Thread jacuzziProcess;
    private Thread televisionProcess;
    private Thread lightingProcess;
    private Thread mediaPlayerProcess;
    private DNSServiceDiscovery serviceDiscovery;
    private boolean isServicesRunning;
    private boolean isJacuzziOn;


    private LaunchControl() throws InterruptedException {
        subscribeToServices();
    }

    public void launchServices() {

        if (!isServicesRunning) {
            try {
                launchJacuzzi();
                launchLighting();
                launchTelevision();
                launchMediaPlayer();

                Thread.sleep(4000);

                isServicesRunning = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isServiceAvailable(ServiceType serviceType) {
        return serviceDiscovery.hasDiscoveredService(serviceType);
    }

    public void triggerJacuzziService() {
        if (serviceDiscovery.hasDiscoveredService(ServiceType.TCP_JACUZZI)) {
            ServiceOperation operation = new ServiceOperation(isJacuzziOn ? 1 : 0);
            isJacuzziOn = !isJacuzziOn;
            ServiceInfo info = serviceDiscovery.getServiceInfo(ServiceType.TCP_JACUZZI);
            ServiceRequest request = new ServiceRequest(info, operation);
            request.send();
            System.out.println("Test Response: " + request.getResponse());
        } else {
            JOptionPane.showMessageDialog(null, "Failed to trigger Jacuzzi");
        }
    }

    private void subscribeToServices() {
        serviceDiscovery = new DNSServiceDiscovery();
        serviceDiscovery.addServiceListener(ServiceType.TCP_JACUZZI);
        serviceDiscovery.addServiceListener(ServiceType.TCP_LIGHTING);
        serviceDiscovery.addServiceListener(ServiceType.TCP_TELEVISION);
    }

    private void launchJacuzzi() throws InterruptedException {
        jacuzziProcess = new Thread(new JacuzziServiceImpl("jacuzziservice1"));
        jacuzziProcess.start();

        Thread.sleep(4000);
    }

    private void launchLighting() throws InterruptedException {
        lightingProcess = new Thread(new LightingServiceImpl("lightingservice1"));
        lightingProcess.start();

        Thread.sleep(4000);
    }

    private void launchTelevision() throws InterruptedException {
        televisionProcess = new Thread(new TelevisionHybridService("televisionservice1"));
        televisionProcess.start();

        Thread.sleep(4000);
    }

    private void launchMediaPlayer() throws InterruptedException {
        mediaPlayerProcess = new Thread(new MediaPlayerServiceImpl("mediaplayer"));
        mediaPlayerProcess.start();

        Thread.sleep(4000);
    }

    public static void main(String[] args) throws InterruptedException {
        new ClientUI(new LaunchControl(), "Smart Home");
    }

}
