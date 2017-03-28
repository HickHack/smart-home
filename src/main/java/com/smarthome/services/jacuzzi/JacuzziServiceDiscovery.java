package com.smarthome.services.jacuzzi;

import com.smarthome.services.model.ServiceRequestModel;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service discovery implementation
 * used to find other services.
 */
public class JacuzziServiceDiscovery {

    private JacuzziClient client;

    public JacuzziServiceDiscovery() {
        client = new JacuzziClient();
        setup();
    }

    public void setup() {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Add a service listener
            jmdns.addServiceListener("_lighting._tcp.local.", new Listener());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Listener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            ServiceRequestModel request = new ServiceRequestModel(0, "Tester", 1);

            ServiceInfo info = event.getInfo();
            JacuzziClient.request("127.0.0.1", info.getPort(), request);
        }
    }
}
