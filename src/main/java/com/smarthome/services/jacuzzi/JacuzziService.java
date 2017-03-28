package com.smarthome.services.jacuzzi;

import com.google.gson.Gson;
import com.smarthome.services.model.ServiceRequestModel;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service main entry point
 */
public class JacuzziService implements JacuzziServerListener {

    private int port;
    private JacuzziServer server;
    private JacuzziController controller;
    private JacuzziServiceDiscovery serviceDiscovery;
    private Gson gson;

    public JacuzziService(int port) {
        this.port = port;
        server = new JacuzziServer(port);
        controller = new JacuzziController();
        serviceDiscovery = new JacuzziServiceDiscovery();
        gson = new Gson();
    }

    public void launch() {
        server.addListener(this);
        register();
        server.start();
    }

    @Override
    public String processRequest() {
        ServiceRequestModel request = gson.fromJson(server.getRequest(), ServiceRequestModel.class);
        return gson.toJson(controller.performOperation(request));
    }

    private void register() {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            ServiceInfo serviceInfo = ServiceInfo.create("_jacuzzi._tcp.local.", "Jacuzzi_Service", port, "");
            jmdns.registerService(serviceInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JacuzziService jacuzziService = new JacuzziService(9090);
        jacuzziService.launch();
    }
}
