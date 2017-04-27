package com.smarthome.services.service.tcp.discovery;

import com.smarthome.services.service.tcp.TCPService;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;


/**
 * @author Graham Murray
 * @descripion jmDNS Service registration
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

    /**
     * Register a service with jmDNS
     * @param service
     */
    public void register(TCPService service) {
        try {
            ServiceInfo serviceInfo = ServiceInfo.create(service.getType().toString(),
                                                         service.getName(),
                                                         service.getPort(), "");
            jmdns.registerService(serviceInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Unregister a service
     * @param service
     */
    public void unregister(TCPService service) {
        ServiceInfo serviceInfo = ServiceInfo.create(service.getType().toString(),
                                                     service.getName(),
                                                     service.getPort(), "");
        jmdns.unregisterService(serviceInfo);
    }
}
