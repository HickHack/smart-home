package com.smarthome.services.lighting;

import com.smarthome.services.jacuzzi.JacuzziController;
import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Lighting service main entry point used for starting the service
 */
public class LightingService implements Service {

    private TCPService tcpService;

    public LightingService(String name, int port) {
        tcpService = new TCPServiceImpl(name, port, ServiceType.LIGHTING);
        tcpService.setController(new JacuzziController(tcpService));
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
        LightingService lightingService = new LightingService("LightingService1", 9091);
        lightingService.start();
    }
}

