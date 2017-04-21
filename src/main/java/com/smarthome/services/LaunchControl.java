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

/**
 * @author Graham Murray
 * @descripion Controller used by the client UI to launch and control
 * services
 *
 */
public class LaunchControl {

    private Thread jacuzziProcess;
    private Thread televisionProcess;
    private Thread lightingProcess;
    private Thread mediaPlayerProcess;
    private DNSServiceDiscovery serviceDiscovery;

    private LaunchControl() throws InterruptedException {
        subscribeToServices();
    }

    public void launchServices() {
        try {
            launchJacuzzi();
            launchLighting();
            launchTelevision();
            launchMediaPlayer();

            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {

        if (jacuzziProcess != null && lightingProcess != null
                && televisionProcess != null && mediaPlayerProcess != null) {
            jacuzziProcess.interrupt();
            lightingProcess.interrupt();
            televisionProcess.interrupt();
            mediaPlayerProcess.interrupt();
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

    private void testJacuzziService() {
        if (serviceDiscovery.hasDiscoveredService(ServiceType.TCP_JACUZZI)) {
            ServiceInfo info = serviceDiscovery.getServiceInfo(ServiceType.TCP_JACUZZI);
            ServiceRequest request = new ServiceRequest(info, new ServiceOperation(0));
            request.send();
            System.out.println("Test Response: " + request.getResponse());
        } else {
            System.out.println("Unable to turn Jacuzzi On!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ClientUI clientUI = new ClientUI(new LaunchControl(), "Smart Home");
        clientUI.init();
    }
}
