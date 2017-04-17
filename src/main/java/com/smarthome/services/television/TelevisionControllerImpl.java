package com.smarthome.services.television;

import com.google.gson.Gson;
import com.smarthome.services.jacuzzi.JacuzziControllerImpl;
import com.smarthome.services.mediaplayer.model.MediaPlayerModel;
import com.smarthome.services.service.*;
import com.smarthome.services.television.model.TelevisionModel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.*;

import static com.smarthome.services.service.ServiceResponse.Status;
import static com.smarthome.services.service.config.Config.BROKER;
import static com.smarthome.services.service.config.Config.PERSISTENCE;
import static com.smarthome.services.service.config.Config.QOS;

/**
 * @author Ian Cunningham
 */
public class TelevisionControllerImpl implements ServiceController {

    private TelevisionModel model;
    private MediaPlayerModel mpModel;
    private Service service;
    private Timer timer;

    public TelevisionControllerImpl(TCPService service) {
        model = new TelevisionModel(service.getName(), service.getPort());
        mpModel = new MediaPlayerModel(service.getName());
        this.service = service;
        timer = new Timer();
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation request) {
        Status status;

        switch (request.getOperationCode()) {
            case 0:
                status = turnTelevisionOn();
                break;
            case 1:
                status = turnTelevisionOff();
                break;
            case 4:
                status = decreaseVolume();
                break;
            case 5:
                status = increaseVolume();
                break;
            default:
                status = Status.UNSUPPORTED_OPERATION;
                break;

        }

        return new ServiceResponse(status, model, service.getType());
    }

    @Override
    public Map getControllerStatus() {
        return model.getValuesMap();
    }

    private Status turnTelevisionOn() {
        if (!model.isTelevisionOn()) {
            model.setTelevisionOn(true);
            model.setVolume(50);
            model.setScreenBrightness(60);
            service.updateUIOutput("Turning TV On. Volume: " + model.getVolume());
            turnMediaPlayerOn();

            timer.schedule(new TelevisionControllerImpl.MediaPlayerTask(), 0, 4000);

            return Status.OK;
        }

        return Status.FAILED;
    }

    private void turnMediaPlayerOn() {
        ServiceOperation operation = new ServiceOperation(0);
        sendServiceOperation(operation);
    }

    private void sendServiceOperation(ServiceOperation operation) {

        Gson gson = new Gson();
        String json = gson.toJson(operation);

        try {
            MqttClient sampleClient = new MqttClient(BROKER, "Television Publisher", PERSISTENCE);
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

    private Status turnTelevisionOff() {
        if (model.isTelevisionOn()) {
            model.setTelevisionOn(false);
            model.setVolume(0);
            model.setScreenBrightness(0);
            service.updateUIOutput("Turning TV Off. Volume: " + model.getVolume());

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status decreaseVolume() {
        if (model.isTelevisionOn() && model.getVolume() > 0) {

            /*if (model.getVolume() - 2 == 0) {
                model.setVolume(0);
                model.setMuteOn(true);
            }*/

            model.setVolume(model.getVolume() - 4);
            service.updateUIOutput("Decreasing volume. Volume: " + model.getVolume());

            return Status.OK;
        }

        model.setVolume(0);
        model.setMuteOn(true);
        service.updateUIOutput("Cant decrease volume. Volume: " + model.getVolume());
        return Status.FAILED;
    }

    private Status increaseVolume() {
        if (model.isTelevisionOn() && model.getVolume() < 100) {

            model.setMuteOn(false);

            /*if (model.getVolume() + 2 == 100) {
                model.setVolume(100);
            }*/

            model.setVolume(model.getVolume() + 4);
            service.updateUIOutput("Increasing volume. Volume" + model.getVolume());

            return Status.OK;
        }

        model.setVolume(100);
        service.updateUIOutput("Cant increase volume. Volume: " + model.getVolume());
        return Status.FAILED;
    }

    class MediaPlayerTask extends TimerTask {

        int i;

        @Override
        public void run() {
            i = mpModel.getTrack() + 1;

            if (model.isTelevisionOn() && i <= 20) {
                mpModel.setTrack(i);
                //service.updateUIStatus();
                service.updateUIOutput("Media Player track: "+ i);
            } else {
                timer.cancel();
                service.updateUIOutput("Media Player played all tracks.");
            }
        }
    }
}
