package com.smarthome.services.lighting;

import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Lighting service main entry point used for starting the service
 */
public class LightingServiceImpl implements Service {

    private TCPService tcpService;

    public LightingServiceImpl(String name, int port) {
        tcpService = new TCPServiceImpl(name, port, ServiceType.LIGHTING);
        tcpService.setController(new LightingControllerImpl(tcpService));
    }

    @Override
    public void stop() {
        tcpService.stop();
    }

    @Override
    public void start() {
        tcpService.start();
    }

    public static void main(String[] args) {
        LightingServiceImpl lightingService = new LightingServiceImpl("LightingService1", 9091);
        lightingService.start();
    }
}

