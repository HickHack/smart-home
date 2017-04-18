package com.smarthome.services;

import com.google.gson.Gson;
import com.smarthome.services.jacuzzi.JacuzziServiceImpl;
import com.smarthome.services.lighting.LightingServiceImpl;
import com.smarthome.services.mediaplayer.MediaPlayerControllerImpl;
import com.smarthome.services.mediaplayer.MediaPlayerServiceImpl;
import com.smarthome.services.service.*;
import com.smarthome.services.service.tcp.discovery.DNSServiceDiscovery;
import com.smarthome.services.television.TelevisionServiceImpl;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.jmdns.ServiceInfo;

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
    public Thread mediaPlayerProcess;
    private DNSServiceDiscovery serviceDiscovery;

    String clientId = "Publisher";

    private LaunchControl() throws InterruptedException {
        subscribeToServices();
        launchJacuzzi();
        launchLighting();
        launchTelevision();
        launchMediaPlayer();
        Thread.sleep(4000);
        testJacuzziService();
        //testMediaPlayerService();

        shutdown();
    }

    private void subscribeToServices() {
        serviceDiscovery = new DNSServiceDiscovery();
        serviceDiscovery.addServiceListener(ServiceType.JACUZZI);
    }

    private void launchJacuzzi() throws InterruptedException {
        jacuzziProcess = new Thread(new JacuzziServiceImpl("jacuzziservice1"));
        jacuzziProcess.start();

        Thread.sleep(4000);
    }

    private void launchLighting() throws InterruptedException {
        lightingProcess = new Thread(new LightingServiceImpl("lightingservice1"));
        lightingProcess.start();

        Thread.sleep(4000);
    }

    private void launchTelevision() throws InterruptedException {
        televisionProcess = new Thread(new TelevisionServiceImpl("televisionservice1"));
        televisionProcess.start();

        Thread.sleep(4000);
    }

    private void launchMediaPlayer() throws InterruptedException {
        mediaPlayerProcess = new Thread(new MediaPlayerServiceImpl("mediaplayer"));
        mediaPlayerProcess.start();

        Thread.sleep(4000);
    }

    private void testJacuzziService() {
        if (serviceDiscovery.hasDiscoveredService(ServiceType.JACUZZI)) {
            ServiceInfo info = serviceDiscovery.getServiceInfo(ServiceType.JACUZZI);
            ServiceRequest request = new ServiceRequest(info, new ServiceOperation(0));
            request.send();
            System.out.println("Test Response: " + request.getResponse());
        } else {
            System.out.println("Unable to turn Jacuzzi On!");
        }
    }

    private void testMediaPlayerService() {

        ServiceOperation operation = new ServiceOperation(0);
        sendServiceOperation(operation);
    }

    private void sendServiceOperation(ServiceOperation operation) {

        Gson gson = new Gson();
        String json = gson.toJson(operation);

        try {
            MqttClient sampleClient = new MqttClient(BROKER, clientId, PERSISTENCE);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to BROKER: " + BROKER);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + json);
            MqttMessage message = new MqttMessage(json.getBytes());
            message.setQos(QOS);
            sampleClient.publish(ServiceType.MEDIA_PLAYER.toString(), message);
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

    public static void main(String[] args) throws InterruptedException {
        new LaunchControl();
    }
}
