package com.smarthome.services.lighting;

import com.smarthome.services.service.tcp.ServiceType;
import com.smarthome.services.service.tcp.TCPServiceImpl;

/**
 * @author Graham Murray
 * @descripion Lighting service main entry point used for starting the service
 */
public class LightingServiceImpl extends TCPServiceImpl{

    public LightingServiceImpl(String name) {
        super(name, ServiceType.TCP_LIGHTING);
    }

    @Override
    public void run() {
        setController(new LightingControllerImpl(this));
        start();
    }
}

