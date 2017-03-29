package com.smarthome.services.service;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


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

    public void register(String type, String name, int port) {
        try {
            ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, "");
            jmdns.registerService(serviceInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
