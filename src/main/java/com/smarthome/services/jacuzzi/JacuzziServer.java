package com.smarthome.services.jacuzzi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service server
 *
 * The event listener implementation using the observer pattern
 *  is based on http://stackoverflow.com/questions/6270132/create-a-custom-event-in-java
 */
public class JacuzziServer {

    private ServerSocket listener;
    private Socket socket;
    private List<JacuzziServerListener> listeners;
    private String request;
    private int port;

    public JacuzziServer(int port) {
        this.port = port;
        listeners = new ArrayList<>();
        request = "";
    }

    public void stop() {
        try {
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void respond(String response) {
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

    public String getRequest() {
        return request;
    }

    public void addListener(JacuzziServerListener listener) {
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
        } finally {
            stop();
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
        for (JacuzziServerListener listener : listeners) {
            respond(listener.processRequest());
        }
    }
}
