package com.smarthome.services.service.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Graham Murray
 * @descripion Server service used for TCP services
 */
public class TCPServiceServer {

    private ServerSocket listener;
    private Socket socket;
    private List<TCPServiceControllerListener> listeners;
    private String request;
    private int port;

    public TCPServiceServer(int port) {
        this.port = port;
        listeners = new ArrayList<>();
        request = "";
    }

    /**
     * Close the SocketListener and Socket
     * which unbind from the port and stops
     * the server looping listening for messages.
     */
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

    public void addListener(TCPServiceControllerListener listener) {
        listeners.add(listener);
    }

    /**
     * Continually loop listening for incoming messages.
     * When a message is received process it.
     * @throws IOException
     */
    private void listen() throws IOException {
        listener = new ServerSocket(port);

        try {
            while (!listener.isClosed()) {
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

    /**
     * Store the incoming message a notify all
     * listeners
     */
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

    /**
     * Inform each listener that a message has been
     * received so they can decide how they want to
     * process the message.
     */
    private void notifyListeners() {
        for (TCPServiceControllerListener listener : listeners) {
            respond(listener.processRequest());
        }
    }

    /**
     * Write a response back to the requester
     * @param response
     */
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
