package com.smarthome.services.jacuzzi;

import com.smarthome.services.service.ServiceType;
import com.smarthome.services.service.tcp.TCPServiceImpl;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service main entry point used for starting the service
 */
public class JacuzziServiceImpl extends TCPServiceImpl {

    public JacuzziServiceImpl(String name) {
        super(name, ServiceType.JACUZZI);
    }

    @Override
    public void run() {
        addSubscriber(ServiceType.LIGHTING);
        addSubscriber(ServiceType.TELEVISION);
        setController(new JacuzziControllerImpl(this));
        start();
    }
}
