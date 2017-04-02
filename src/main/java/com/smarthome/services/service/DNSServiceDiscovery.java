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

    private List<DNSListener> listeners;

    public DNSServiceDiscovery() {
        listeners = new ArrayList<>();
    }

    public ServiceInfo getServiceInfo(ServiceType serviceType) {
        for (DNSListener listener : listeners) {
            if (listener.doesServiceExist(serviceType)) {
                return listener.getServiceByType(serviceType);
            }
        }

        return null;
    }

    public boolean hasDiscoveredService(ServiceType serviceType) {
        for (DNSListener listener : listeners) {
            if (listener.doesServiceExist(serviceType)) {
                return true;
            }
        }

        return false;
    }

    public void addServiceListener(ServiceType serviceType) {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Add a service listener
            DNSListener listener = new DNSListener();
            jmdns.addServiceListener(serviceType.toString(), listener);
            listeners.add(listener);
            Thread.sleep(15000);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private static class DNSListener implements ServiceListener {

        private List<ServiceInfo> services;

        public DNSListener() {
            services = new ArrayList<>();
        }

        public ServiceInfo getServiceByType(ServiceType serviceType) {

            for (ServiceInfo service : services) {
                if (service.getType().equals(serviceType.toString())) {
                    return service;
                }
            }

            return null;
        }

        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added " + event.getInfo().getName());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            removeService(ServiceType.valueOf(event.getInfo().getType()));
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            addService(event.getInfo());
        }

        private void addService(ServiceInfo serviceInfo) {

            if (!doesServiceExist(ServiceType.fromString(serviceInfo.getType()))) {
                services.add(serviceInfo);
            }
        }

        private void removeService(ServiceType serviceType) {
            int position = 0;

            if (doesServiceExist(serviceType)) {
                for(ServiceInfo discoveredService : services) {
                    if (discoveredService.getType().equals(serviceType.toString())) {
                        services.remove(position);
                    }

                    position++;
                }
            }
        }

        private boolean doesServiceExist(ServiceType serviceType) {

             for(ServiceInfo discoveredService : services) {
                if (discoveredService.getType().equals(serviceType.toString())) {
                    return true;
                }
            }

            return false;
        }
    }
}
