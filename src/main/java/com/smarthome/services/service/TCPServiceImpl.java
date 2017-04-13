package com.smarthome.services.service;

import com.google.gson.Gson;
import com.smarthome.services.jacuzzi.model.JacuzziModel;
import com.smarthome.services.lighting.model.LightingModel;
import com.smarthome.services.service.model.BaseServiceModel;
import com.smarthome.services.television.model.TelevisionModel;

import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.ServerSocket;

import static com.smarthome.services.service.ServiceType.*;
import static com.smarthome.services.service.config.Config.MAX_REQUEST_RETRY;

/**
 * @author Graham Murray
 * @date 30/03/17
 */
public class TCPServiceImpl implements TCPService, ServiceControllerListener {

    private String name;
    private int port;
    private ServiceServer server;
    private ServiceController controller;
    private DNSServiceRegistry registry;
    private DNSServiceDiscovery dnsServiceDiscovery;
    private ServiceType serviceType;
    private Gson gson;
    private int requestRetryCount;

    public TCPServiceImpl(String name,
                          ServiceType serviceType) {
        try {
            this.port = findAvailablePort();
            this.name = name;
            this.serviceType = serviceType;
            server = new ServiceServer(port);
            dnsServiceDiscovery = new DNSServiceDiscovery();
            gson = new Gson();

            registry = new DNSServiceRegistry();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public BaseServiceModel connectToService(ServiceOperation operation, ServiceType serviceType) {

        while (requestRetryCount <= MAX_REQUEST_RETRY) {
            if (dnsServiceDiscovery.hasDiscoveredService(serviceType)) {
                ServiceInfo serviceInfo = dnsServiceDiscovery.getServiceInfo(serviceType);

                if (serviceInfo != null) {
                    ServiceRequest request = new ServiceRequest(serviceInfo, operation);
                    request.send();
                    System.out.println(serviceType.toString() + " Response: " + request.getResponse());

                    return deserializeResponse(request.getResponse(), serviceType);
                }
            }

            requestRetryCount++;
        }
        requestRetryCount = 0;

        return null;
    }

    @Override
    public String processRequest() {
        ServiceOperation operation = gson.fromJson(server.getRequest(), ServiceOperation.class);
        return gson.toJson(controller.performOperation(operation));
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        start();
    }

    private int findAvailablePort() throws IOException {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        server.close();

        return port;
    }

    private BaseServiceModel deserializeResponse(String response, ServiceType serviceType) {

        switch (serviceType) {
            case JACUZZI:
                return gson.fromJson(response, JacuzziModel.class);
            case LIGHTING:
                return gson.fromJson(response, LightingModel.class);
            case TELEVISION:
                return gson.fromJson(response, TelevisionModel.class);
        }

        return null;
    }
}
