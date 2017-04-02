package com.smarthome.services.lighting;

import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceType;
import com.smarthome.services.service.TCPService;
import com.smarthome.services.service.TCPServiceImpl;

/**
 * @author Graham Murray
 * @descripion Lighting service main entry point used for starting the service
 */
public class LightingServiceImpl implements Service {

    private TCPService tcpService;
    String name;
    int port;


    public LightingServiceImpl(String name, int port) {
        this.name = name;
        this.port = port;
    }

    @Override
    public void stop() {
        tcpService.stop();
    }

    @Override
    public void start() {
        if (tcpService != null) {
            tcpService.start();
        }
    }

    @Override
    public void run() {
        tcpService = new TCPServiceImpl(name, port, ServiceType.LIGHTING);
        tcpService.setController(new LightingControllerImpl(tcpService));
        start();
    }
}

