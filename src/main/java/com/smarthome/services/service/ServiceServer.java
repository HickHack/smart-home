package com.smarthome.services.service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Graham Murray
 * @descripion Server service used for TCP services
 *
 */
public class ServiceServer {

    private ServerSocket listener;
    private Socket socket;
    private List<ServiceControllerListener> listeners;
    private String request;
    private int port;

    public ServiceServer(int port) {
        this.port = port;
        listeners = new ArrayList<>();
        request = "";
    }

    public void stop() {
        try {
            if (listener != null  && socket != null) {
                listener.close();
                socket.close();
            }
        } catch (IOException e) {
            System.out.print("Failed to stop server.");
        }
    }

    public void start() {
        try {
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRequest() {
        return request;
    }

    public void addListener(ServiceControllerListener listener) {
        listeners.add(listener);
    }

    private void listen() throws IOException {
        listener = new ServerSocket(port);

        try {
            while (true) {
                socket = listener.accept();

                try {
                    processRequest();
                } finally {
                    socket.close();
                }
            }
        } catch (SocketException ex) {
            System.out.println("Server stopped listening");
        }
    }

    private void processRequest() {
        InputStream inputStream;

        try {
            inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            request = br.readLine();

            notifyListeners();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyListeners() {
        for (ServiceControllerListener listener : listeners) {
            respond(listener.processRequest());
        }
    }

    private void respond(String response) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(response);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
