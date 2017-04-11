package com.smarthome.services.service.config;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by Ian C on 07/04/2017.
 */
public class Config {

    public static String broker = "tcp://iot.eclipse.org:1883";
    public static MemoryPersistence persistence = new MemoryPersistence();
    public static String content = "Message from MqttPublishSample";
    public static int qos = 2;

}
