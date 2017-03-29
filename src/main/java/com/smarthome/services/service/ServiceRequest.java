package com.smarthome.services.service;

import com.google.gson.Gson;

import javax.jmdns.ServiceInfo;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by graham on 28/03/17.
 */
public class ServiceRequest {

    private boolean isSuccess;
    private String serviceName;
    private String response;
    private ServiceOperation serviceOperation;
    private DNSServiceDiscovery serviceDiscovery;
    private ServiceInfo serviceInfo;

    public ServiceRequest(String serviceName, ServiceOperation serviceOperation) {
        serviceDiscovery = new DNSServiceDiscovery();
        this.serviceName = serviceName;
        this.serviceOperation = serviceOperation;
        response = "";
    }

    public boolean isServiceRunning() {
        if (serviceDiscovery.doesServiceExist(serviceName)) {
            serviceInfo = serviceDiscovery.getServiceInfo(serviceName);
            return true;
        }

        return false;
    }

    public void send() {
        Gson gson = new Gson();
        String json = gson.toJson(serviceOperation);

        if (isServiceRunning()) {
            try {
                Socket clientSocket = new Socket("127.0.0.1", serviceInfo.getPort());
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                outputStream.writeBytes(json + '\n');
                response = bufferedReader.readLine();
                clientSocket.close();

                isSuccess = true;
            } catch (IOException e) {
                e.printStackTrace();
                isSuccess = false;
            }
        } else {
            isSuccess = false;
        }
    }

    public boolean isSuccessful() {
        return isSuccess;
    }

    public String getResponse() {
        return response;
    }
}
