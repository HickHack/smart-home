package com.smarthome.services;

import com.google.gson.Gson;
import com.smarthome.services.jacuzzi.JacuzziServiceImpl;
import com.smarthome.services.lighting.LightingServiceImpl;
import com.smarthome.services.mediaplayer.MediaPlayerServiceImpl;
import com.smarthome.services.service.*;
import com.smarthome.services.service.ServiceType;
import com.smarthome.services.service.mqtt.MQTTOperations;
import com.smarthome.services.service.tcp.discovery.DNSServiceDiscovery;
import com.smarthome.services.television.TelevisionHybridService;
import com.smarthome.ui.client.ClientUI;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.jmdns.ServiceInfo;
import javax.swing.JOptionPane;

import static com.smarthome.services.service.config.Config.BROKER;
import static com.smarthome.services.service.config.Config.PERSISTENCE;
import static com.smarthome.services.service.config.Config.QOS;

/**
 * @author Graham Murray
 * @descripion Controller used by the client UI to launch and control
 * services
 */
public class LaunchControl {

    private DNSServiceDiscovery serviceDiscovery;
    private boolean isServicesRunning;
    private boolean isJacuzziOn;
    private boolean isLightingOn;
    private boolean isTelevisionOn;
    private boolean isMediaPlayerOn;

    private LaunchControl() throws InterruptedException {
        subscribeToServices();
    }

    public void launchServices() {

        if (!isServicesRunning) {
            try {
                launchJacuzzi();
                launchLighting();
                launchTelevision();
                launchMediaPlayer();

                Thread.sleep(4000);

                isServicesRunning = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isTCPServiceAvailable(ServiceType serviceType) {
        return serviceDiscovery.hasDiscoveredService(serviceType);
    }

    public boolean isMediaPlayerAvailable() {
        return isServicesRunning;
    }

    public void triggerJacuzziService() {
        if (serviceDiscovery.hasDiscoveredService(ServiceType.TCP_JACUZZI)) {
            ServiceOperation operation = new ServiceOperation(isJacuzziOn ? 1 : 0);
            operation.setRequester("Client UI");
            isJacuzziOn = !isJacuzziOn;
            ServiceInfo info = serviceDiscovery.getServiceInfo(ServiceType.TCP_JACUZZI);
            ServiceRequest request = new ServiceRequest(info, operation);
            request.send();
            System.out.println("Jacuzzi Response: " + request.getResponse());
        } else {
            JOptionPane.showMessageDialog(null, "Failed to trigger Jacuzzi");
        }
    }

    public void triggerLightingService() {
        if (serviceDiscovery.hasDiscoveredService(ServiceType.TCP_LIGHTING)) {
            ServiceOperation operation = new ServiceOperation(isLightingOn ? 1 : 0);
            operation.setRequester("Client UI");
            isLightingOn = !isLightingOn;
            ServiceInfo info = serviceDiscovery.getServiceInfo(ServiceType.TCP_LIGHTING);
            ServiceRequest request = new ServiceRequest(info, operation);
            request.send();
            System.out.println("Lighting Response: " + request.getResponse());
        } else {
            JOptionPane.showMessageDialog(null, "Failed to trigger Lighting");
        }
    }

    public void triggerTelevisionService() {
        if (serviceDiscovery.hasDiscoveredService(ServiceType.TCP_TELEVISION)) {
            ServiceOperation operation = new ServiceOperation(isTelevisionOn ? 1 : 0);
            operation.setRequester("Client UI");
            isTelevisionOn = !isTelevisionOn;
            ServiceInfo info = serviceDiscovery.getServiceInfo(ServiceType.TCP_TELEVISION);
            ServiceRequest request = new ServiceRequest(info, operation);
            request.send();
            System.out.println("Lighting Response: " + request.getResponse());
        } else {
            JOptionPane.showMessageDialog(null, "Failed to trigger Television");
        }
    }

    public void triggerMediaPlayerService() {
        ServiceOperation operation = new ServiceOperation(isTelevisionOn ? 1 : 0);
        operation.setRequester("Client UI");
        isMediaPlayerOn = !isMediaPlayerOn;
        sendMQTTMessage(operation);
    }

    private void subscribeToServices() {
        serviceDiscovery = new DNSServiceDiscovery();
        serviceDiscovery.addServiceListener(ServiceType.TCP_JACUZZI);
        serviceDiscovery.addServiceListener(ServiceType.TCP_LIGHTING);
        serviceDiscovery.addServiceListener(ServiceType.TCP_TELEVISION);
    }

    private void launchJacuzzi() throws InterruptedException {
        Thread jacuzziProcess = new Thread(new JacuzziServiceImpl("jacuzziservice"));
        jacuzziProcess.start();

        Thread.sleep(4000);
    }

    private void launchLighting() throws InterruptedException {
        Thread lightingProcess = new Thread(new LightingServiceImpl("lightingservice"));
        lightingProcess.start();

        Thread.sleep(4000);
    }

    private void launchTelevision() throws InterruptedException {
        Thread televisionProcess = new Thread(new TelevisionHybridService("televisionservice"));
        televisionProcess.start();

        Thread.sleep(4000);
    }

    private void launchMediaPlayer() throws InterruptedException {
        Thread mediaPlayerProcess = new Thread(new MediaPlayerServiceImpl("mediaplayer"));
        mediaPlayerProcess.start();

        Thread.sleep(4000);
    }

    private void sendMQTTMessage(ServiceOperation operation) {

        Gson gson = new Gson();
        String json = gson.toJson(operation);

        try {
            MqttClient client = new MqttClient(BROKER, "UI Client", PERSISTENCE);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
            MqttMessage message = new MqttMessage(json.getBytes());
            message.setQos(QOS);
            client.publish(ServiceType.MQTT_MEDIA_PLAYER.toString(), message);
            client.disconnect();
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ClientUI(new LaunchControl(), "Smart Home");
    }

}
