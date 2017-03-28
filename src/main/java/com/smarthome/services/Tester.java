package com.smarthome.services;

import com.google.gson.Gson;
import com.smarthome.services.model.ServiceRequestModel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by graham on 28/03/17.
 */
public class Tester {
    private static Gson gson;

    public static void main(String[] args) throws IOException {
        gson = new Gson();
        connectToService(9090);
    }

    public static void connectToService(int port) throws IOException {
        ServiceRequestModel request = new ServiceRequestModel(0, "Tester", 1);
        String json = gson.toJson(request);

        Socket clientSocket = new Socket("localhost", port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer.writeBytes(json + '\n');
        System.out.println("Response: " + inFromServer.readLine());
        clientSocket.close();
    }
}
