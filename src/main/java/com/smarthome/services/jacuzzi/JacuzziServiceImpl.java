package com.smarthome.services.jacuzzi;

import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceType;
import com.smarthome.services.service.TCPService;
import com.smarthome.services.service.TCPServiceImpl;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service main entry point used for starting the service
 */
public class JacuzziServiceImpl implements Service {

    private TCPService tcpService;
    String name;

    public JacuzziServiceImpl(String name) {
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
        tcpService = new TCPServiceImpl(name, ServiceType.JACUZZI);
        tcpService.addSubscriber(ServiceType.LIGHTING);
        tcpService.addSubscriber(ServiceType.TELEVISION);
        tcpService.setController(new JacuzziControllerImpl(tcpService));
        start();
    }
}
