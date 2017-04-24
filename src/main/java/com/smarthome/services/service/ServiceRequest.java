package com.smarthome.services.service;

import com.google.gson.Gson;

import javax.jmdns.ServiceInfo;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static com.smarthome.services.service.config.Config.MAX_REQUEST_TIMEOUT;

/**
 * @author Graham Murray
 */
public class ServiceRequest {

    private boolean isSuccess;
    private boolean isTimeout;
    private String response;
    private ServiceOperation serviceOperation;
    private ServiceInfo serviceInfo;

    public ServiceRequest(ServiceInfo serviceInfo, ServiceOperation serviceOperation) {
        this.serviceInfo = serviceInfo;
        this.serviceOperation = serviceOperation;
        response = "";
    }

    /**
     * Send a serialized ServiceOperation to a
     * specified service and set whether is was successful or not
     * and whether request timed out.
     */
    public void send() {
        Gson gson = new Gson();
        String json = gson.toJson(serviceOperation);

            try {
                Socket clientSocket = new Socket();
                InetSocketAddress socketAddress = new InetSocketAddress(serviceInfo.getHostAddresses()[0], serviceInfo.getPort());
                clientSocket.setSoTimeout(MAX_REQUEST_TIMEOUT);
                clientSocket.connect(socketAddress, MAX_REQUEST_TIMEOUT);
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                outputStream.writeBytes(json + '\n');
                response = bufferedReader.readLine();
                clientSocket.close();

                isSuccess = true;
            } catch (IOException e) {
                isSuccess = false;

                if (e instanceof SocketTimeoutException) {
                    isTimeout = true;
                }
            }
    }

    public boolean isSuccessful() {
        return isSuccess;
    }

    public boolean isTimeout() {
        return isTimeout;
    }

    public String getResponse() {
        return response;
    }
}
