package com.smarthome.services.television;

import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceType;
import com.smarthome.services.service.TCPService;
import com.smarthome.services.service.TCPServiceImpl;

/**
 * Created by Ian C on 01/04/2017.
 */
public class TelevisionServiceImpl implements Service {

    private TCPService tcpService;
    String name;
    int port;

    public TelevisionServiceImpl(String name) {
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
        tcpService = new TCPServiceImpl(name, ServiceType.TELEVISION);
        tcpService.setController(new TelevisionControllerImpl(tcpService));
        start();
    }
}
