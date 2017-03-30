package com.smarthome.services.service;

import com.google.gson.Gson;

import javax.jmdns.ServiceInfo;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Graham Murray
 * @descripion
 */
public class ServiceRequest {

    private boolean isSuccess;
    private String response;
    private ServiceOperation serviceOperation;
    private ServiceInfo serviceInfo;

    public ServiceRequest(ServiceInfo serviceInfo, ServiceOperation serviceOperation) {
        this.serviceInfo = serviceInfo;
        this.serviceOperation = serviceOperation;
        response = "";
    }

    public void send() {
        Gson gson = new Gson();
        String json = gson.toJson(serviceOperation);

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
    }

    public boolean isSuccessful() {
        return isSuccess;
    }

    public String getResponse() {
        return response;
    }
}
