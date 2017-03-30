package com.smarthome.services.jacuzzi;

import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service main entry point used for starting the service
 */
public class JacuzziServiceImpl implements Service {

    private TCPService tcpService;
    String name;
    int port;

    public JacuzziServiceImpl(String name, int port) {
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
        tcpService = new TCPServiceImpl(name, port, ServiceType.JACUZZI);
        tcpService.addSubscriber(ServiceType.LIGHTING);
        tcpService.setController(new JacuzziControllerImpl(tcpService));
        start();
    }
}
