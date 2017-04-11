package com.smarthome.services;

import com.google.gson.Gson;
import com.smarthome.services.jacuzzi.JacuzziServiceImpl;
import com.smarthome.services.lighting.LightingServiceImpl;
import com.smarthome.services.mediaplayer.MediaPlayerServiceImpl;
import com.smarthome.services.service.*;
import com.smarthome.services.television.TelevisionServiceImpl;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static com.smarthome.services.service.config.Config.*;

/**
 * @author Graham Murray
 * @descripion Utility class for testing running services.
 *
 */
public class LaunchControl {

    private Thread jacuzziProcess;
    private Thread televisionProcess;
    private Thread lightingProcess;
    private Thread mediaPlayerProcess;

    String clientId = "Publisher";

    private LaunchControl() throws InterruptedException {
        launchJacuzzi();
        launchLighting();
        launchTelevision();
        launchMediaPlayer();

        testJacuzziService();
        testMediaPlayerService();

        shutdown();
    }

    private void launchJacuzzi() throws InterruptedException {
        jacuzziProcess = new Thread(new JacuzziServiceImpl("Jacuzzi_Service1", 9090));
        jacuzziProcess.start();

        Thread.sleep(9000);
    }

    private void launchLighting() throws InterruptedException {
        lightingProcess = new Thread(new LightingServiceImpl("LightingService1", 9091));
        lightingProcess.start();

        Thread.sleep(9000);
    }

    private void launchTelevision() throws InterruptedException {
        televisionProcess = new Thread(new TelevisionServiceImpl("TelevisionService1", 9092));
        televisionProcess.start();

        Thread.sleep(9000);
    }

    private void launchMediaPlayer() throws InterruptedException {
        mediaPlayerProcess = new Thread(new MediaPlayerServiceImpl());
        mediaPlayerProcess.start();

        Thread.sleep(9000);
    }

    private void testJacuzziService() {

        ServiceOperation operation = new ServiceOperation(0);
        String response = sendRequest(operation, 9090);

        System.out.println("Test Response: " + response);
    }

    private void testMediaPlayerService() {

        ServiceOperation operation = new ServiceOperation(0);
        sendServiceOperation(operation);
    }

    private void sendServiceOperation(ServiceOperation operation) {

        Gson gson = new Gson();
        String json = gson.toJson(operation);

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + json);
            MqttMessage message = new MqttMessage(json.getBytes());
            message.setQos(qos);
            sampleClient.publish(ServiceType.MEDIAPLAYER.toString(), message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void shutdown() {
        jacuzziProcess.interrupt();
        lightingProcess.interrupt();
        televisionProcess.interrupt();
        mediaPlayerProcess.interrupt();
    }

    public String sendRequest(ServiceOperation serviceOperation, int port){
        Gson gson = new Gson();
        String json = gson.toJson(serviceOperation);
        String response = "";

        try {
            Socket clientSocket = new Socket("127.0.0.1", port);
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outputStream.writeBytes(json + '\n');
            response = bufferedReader.readLine();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static void main(String[] args) throws InterruptedException {
        new LaunchControl();
    }
}
