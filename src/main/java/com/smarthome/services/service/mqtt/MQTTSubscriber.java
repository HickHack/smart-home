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
public class MQTTSubscriber implements MqttCallback {

    private MQTTService service;
    private Gson gson;

    public MQTTSubscriber(MQTTService service) {
        this.gson = new Gson();
        this.service = service;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ServiceResponse serviceResponse = service.getController().performOperation(gson.fromJson(mqttMessage.toString(), ServiceOperation.class));

        String serializedResponse = gson.toJson(serviceResponse);
        service.publish(serializedResponse);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
