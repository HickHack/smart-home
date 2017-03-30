package com.smarthome.services.jacuzzi;

import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service main entry point used for starting the service
 */
public class JacuzziServiceImpl implements Service {

    private TCPService tcpService;

    public JacuzziServiceImpl(String name, int port) {
        tcpService = new TCPServiceImpl(name, port, ServiceType.JACUZZI);
        tcpService.addSubscriber(ServiceType.LIGHTING);
        tcpService.setController(new JacuzziControllerImpl(tcpService));
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
        JacuzziServiceImpl jacuzziService = new JacuzziServiceImpl("Jacuzzi_Service1", 9090);
        jacuzziService.start();
    }
}
