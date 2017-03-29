package com.smarthome.services.service;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service discovery implementation
 * used to find other services.
 */
public class DNSServiceDiscovery {

    private Listener serviceListener;

    public DNSServiceDiscovery(ServiceRequest serviceRequest) {
        serviceListener = new Listener();
        setup();
    }

    public ServiceInfo getServiceInfo(String serviceName) {
        return serviceListener.getServiceByName(serviceName);
    }

    public boolean doesServiceExist(String serviceName) {
        return serviceListener.doesServiceExist(serviceName);
    }

    private void setup() {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Add a service listener
            jmdns.addServiceListener("_smart_home._tcp.local.", serviceListener);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Listener implements ServiceListener {

        private List<ServiceInfo> services;

        public Listener() {
            services = new ArrayList<>();
        }

        public ServiceInfo getServiceByName(String serviceName) {

            for (ServiceInfo service : services) {
                if (service.getName().equals(serviceName)) {
                    return service;
                }
            }

            return null;
        }

        @Override
        public void serviceAdded(ServiceEvent event) {
            addService(event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            removeService(event.getInfo().getName());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            addService(event.getInfo());
        }

        private void addService(ServiceInfo serviceInfo) {

            if (!doesServiceExist(serviceInfo.getName())) {
                services.add(serviceInfo);
            }
        }

        private void removeService(String serviceName) {
            int position = 0;

            if (doesServiceExist(serviceName)) {
                for(ServiceInfo discoveredService : services) {
                    if (discoveredService.getName().equals(serviceName)) {
                        services.remove(position);
                    }

                    position++;
                }
            }
        }

        private boolean doesServiceExist(String serviceName) {

            for(ServiceInfo discoveredService : services) {
                if (discoveredService.getName().equals(serviceName)) {
                    return true;
                }
            }

            return false;
        }
    }
}
