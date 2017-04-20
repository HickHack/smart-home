package com.smarthome.services.mediaplayer;

import com.google.gson.Gson;
import com.smarthome.services.service.ServiceOperation;
import com.smarthome.services.service.ServiceResponse;
import com.smarthome.services.service.mqtt.MQTTService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Ian Cunningham
 */
public class MediaPlayerMQTTCallback implements MqttCallback {

    private MQTTService service;
    private Gson gson;

    public MediaPlayerMQTTCallback() {
        this.gson = new Gson();
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ServiceOperation operation = gson.fromJson(mqttMessage.toString(), ServiceOperation.class);
        ServiceResponse serviceResponse = service.getController().performOperation(operation);
        service.publish(serviceResponse);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public void setService(MQTTService service) {
        this.service = service;
    }
}
