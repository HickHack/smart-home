package com.smarthome.services.television;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.smarthome.services.mediaplayer.model.MediaPlayerModel;
import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceResponse;
import com.smarthome.services.service.model.deserialize.ServiceResponseDeserializer;
import com.smarthome.services.service.mqtt.MQTTService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Ian Cunningham
 */
public class TelevisionMQTTCallback implements MqttCallback {

    private MQTTService service;
    private Gson gson;

    public TelevisionMQTTCallback(MQTTService service) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(ServiceResponse.class, new ServiceResponseDeserializer())
                .create();
        this.service = service;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ServiceResponse operation = gson.fromJson(mqttMessage.toString(), ServiceResponse.class);
        service.updateUIOutput("Received mqtt message from " + operation.getType() + " Status " + operation.getStatus());
        ((TelevisionControllerImpl) service.getController()).pickSong(operation);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
