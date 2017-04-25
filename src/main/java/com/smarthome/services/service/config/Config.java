package com.smarthome.services.service.config;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Ian Cunningham
 */
public class Config {

    /**
     * MQTT SERVICE CONSTANTS
     */
    public static String BROKER = "tcp://iot.eclipse.org:1883";
    public static MemoryPersistence PERSISTENCE = new MemoryPersistence();
    public static int QOS = 2;

    /**
     * TCP SERVICE CONSTANTS
     */
    public static int MAX_REQUEST_RETRY = 3;
    public static int MAX_REQUEST_TIMEOUT = 1500;
}
