package com.smarthome.services.service;

import com.google.gson.Gson;

import javax.jmdns.ServiceInfo;

/**
 * Created by graham on 30/03/17.
 */
public class TCPServiceImpl implements TCPService, ServiceServerListener {

    private String name;
    private int port;
    private ServiceServer server;
    private ServiceController controller;
    private DNSServiceRegistry registry;
    private DNSServiceDiscovery dnsServiceDiscovery;
    private ServiceType serviceType;
    private Gson gson;

    public TCPServiceImpl(String name,
                          int port,
                          ServiceType serviceType) {
        this.port = port;
        this.name = name;
        this.serviceType = serviceType;
        server = new ServiceServer(port);
        dnsServiceDiscovery = new DNSServiceDiscovery();
        gson = new Gson();

        registry = new DNSServiceRegistry();
    }

    @Override
    public void stop() {
        server.stop();
        //TODO Unregister JMDNS
    }

    @Override
    public void start() {
        register();
        server.addListener(this);
        server.start();
    }

    @Override
    public void register() {
        registry.register(serviceType.toString(), name, port);
    }

    @Override
    public void addSubscriber(ServiceType subscriberType) {
        dnsServiceDiscovery.addServiceListener(subscriberType);
    }

    @Override
    public void setController(ServiceController controller) {
        this.controller = controller;
    }

    @Override
    public String connectToService(ServiceOperation operation, ServiceType serviceType) {
        //TODO: Add error handling

        if (dnsServiceDiscovery.hasDiscoveredService(serviceType)) {
            ServiceInfo serviceInfo = dnsServiceDiscovery.getServiceInfo(serviceType);

            if (serviceInfo != null) {
                ServiceRequest request = new ServiceRequest(serviceInfo, operation);
                request.send();
                System.out.println("Service Response: " + request.getResponse());

                return request.getResponse();
            }
        }

        return null;
    }

    @Override
    public String processRequest() {
        ServiceOperation operation = gson.fromJson(server.getRequest(), ServiceOperation.class);
        return gson.toJson(controller.performOperation(operation));
    }

    @Override
    public void run() {
        start();
    }
}
