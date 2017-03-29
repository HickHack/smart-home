package com.smarthome.services.jacuzzi;

import com.google.gson.Gson;
import com.smarthome.services.service.*;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service main entry point used for starting the service
 */
public class JacuzziService implements Service, ServiceServerListener {

    private int port;
    private ServiceServer server;
    private JacuzziController controller;
    private DNSServiceRegistry registry;
    private Gson gson;
    private DNSServiceDiscovery serviceDiscovery;

    public JacuzziService(int port) {
        this.port = port;
        server = new ServiceServer(port);
        controller = new JacuzziController();
        gson = new Gson();
        registry = new DNSServiceRegistry();
    }

    @Override
    public void launch() {
        server.addListener(this);
        register();
        server.start();
    }

    @Override
    public String processRequest() {
        ServiceOperation operation = gson.fromJson(server.getRequest(), ServiceOperation.class);
        return gson.toJson(controller.performOperation(operation));
    }

    @Override
    public void register() {
        registry.register("_smart_home._tcp.local.", "Jacuzzi_Service", port);
    }

    public static void main(String[] args) {
        JacuzziService jacuzziService = new JacuzziService(9090);
        jacuzziService.launch();
    }
}
