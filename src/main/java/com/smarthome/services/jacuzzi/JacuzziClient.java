package com.smarthome.services.jacuzzi;

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
public class JacuzziClient {

    public JacuzziClient() {
    }

    public static String request(String host, int port, ServiceRequestModel request) {
        Gson gson = new Gson();

        String json = gson.toJson(request);
        String response = "";
        try {
            Socket clientSocket = new Socket(host, port);
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outputStream.writeBytes(json + '\n');
            response = bufferedReader.readLine();
            System.out.println("Response: " + response);
            clientSocket.close();

            response = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
