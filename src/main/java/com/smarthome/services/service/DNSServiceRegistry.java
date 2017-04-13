package com.smarthome.services.service;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;


/**
 * @author Graham Murray
 * @descripion
 */

public class DNSServiceRegistry {

    private JmDNS jmdns;

    public DNSServiceRegistry() {
        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void register(Service service) {
        try {
            ServiceInfo serviceInfo = ServiceInfo.create(service.getType().toString(),
                                                         service.getName(),
                                                         service.getPort(), "");
            jmdns.registerService(serviceInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unregister(Service service) {
        ServiceInfo serviceInfo = ServiceInfo.create(service.getType().toString(),
                                                     service.getName(),
                                                     service.getPort(), "");
        jmdns.unregisterService(serviceInfo);
    }
}
