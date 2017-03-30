package com.smarthome.services.jacuzzi;

import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service main entry point used for starting the service
 */
public class JacuzziService implements Service {

    private TCPService tcpService;

    public JacuzziService(String name, int port) {
        tcpService = new TCPServiceImpl(name, port, ServiceType.JACUZZI);
        tcpService.addSubscriber(ServiceType.LIGHTING);
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
        JacuzziService jacuzziService = new JacuzziService("Jacuzzi_Service1", 9090);
        jacuzziService.start();
    }
}
