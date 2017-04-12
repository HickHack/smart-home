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

    public LightingServiceImpl(String name) {
        this.name = name;
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
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        tcpService = new TCPServiceImpl(name, ServiceType.LIGHTING);
        tcpService.setController(new LightingControllerImpl(tcpService));
        start();
    }
}

