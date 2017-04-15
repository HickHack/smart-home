package com.smarthome.services.service;

import com.google.gson.Gson;
import com.smarthome.services.jacuzzi.model.JacuzziModel;
import com.smarthome.services.lighting.model.LightingModel;
import com.smarthome.services.service.model.BaseServiceModel;
import com.smarthome.services.television.model.TelevisionModel;
import com.smarthome.ui.ServiceUI;

import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.ServerSocket;

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
    private ServiceUI ui;
    private int requestRetryCount;

    public TCPServiceImpl(String name,
                          ServiceType serviceType) {
        try {
            this.port = findAvailablePort();
            this.name = name;
            this.serviceType = serviceType;
            server = new ServiceServer(port);
            dnsServiceDiscovery = new DNSServiceDiscovery();
            ui = new ServiceUI(this);
            gson = new Gson();

            registry = new DNSServiceRegistry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        registry.unregister(this);
        server.stop();
        updateUIOutput("Stopping service...");
        Thread.currentThread().interrupt();
    }

    @Override
    public void start() {
        ui.init();
        registry.register(this);
        server.addListener(this);
        updateUIOutput(name + " is starting on port " + port);
        updateUIStatus();
        server.start();
    }

    @Override
    public void updateUIOutput(String message) {
        updateUIStatus();
        ui.updateOutput(message);
    }

    @Override
    public void updateUIStatus() {
        ui.updateStatusAttributes(controller.getControllerStatus());
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
    public ServiceResponse connectToService(ServiceOperation operation, ServiceType serviceType) {
        operation.setRequester(name);

        while (requestRetryCount < MAX_REQUEST_RETRY) {
            if (dnsServiceDiscovery.hasDiscoveredService(serviceType)) {
                ServiceInfo serviceInfo = dnsServiceDiscovery.getServiceInfo(serviceType);

                if (serviceInfo != null) {
                    ServiceRequest request = new ServiceRequest(serviceInfo, operation);
                    updateUIOutput("Sending request to " + serviceInfo.getName() + " opcode: " + operation.getOperationCode());
                    request.send();

                    if (request.isSuccessful()) {
                        return gson.fromJson(request.getResponse(), ServiceResponse.class);
                    } else {
                        ui.updateOutput("Failed to connect to " + serviceInfo.getName() + " on port " + serviceInfo.getPort());
                    }
                }
            } else {
                ui.updateOutput("Yet to discover " + serviceType);
                break;
            }

            requestRetryCount++;
            ui.updateOutput("Request retry: " + requestRetryCount);
        }

        requestRetryCount = 0;

        return null;
    }

    @Override
    public String processRequest() {
        ServiceOperation operation = gson.fromJson(server.getRequest(), ServiceOperation.class);
        updateUIOutput("Request received from " + operation.getRequester() + " - opcode: " + operation.getOperationCode());

        ServiceResponse response = controller.performOperation(operation);
        ui.updateStatusAttributes(controller.getControllerStatus());

        return gson.toJson(response);
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
    public ServiceType getType() {
        return serviceType;
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
