package com.smarthome.services.service.mqtt;

import com.google.gson.Gson;
import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceOperation;
import com.smarthome.services.service.ServiceResponse;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Ian Cunningham
 */
public class MQTTServiceCallback implements MqttCallback {

    private MQTTService service;
    private Gson gson;

    public MQTTServiceCallback(MQTTService service) {
        this.gson = new Gson();
        this.service = service;
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
}
