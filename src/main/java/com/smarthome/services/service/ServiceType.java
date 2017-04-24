package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @descripion Enum for storing service types. In the
 * case of a TCP service its the domain name while for
 * an MQTT service it's the topic.
 */
public enum ServiceType {
    TCP_JACUZZI("_jacuzzi._tcp.local."),
    TCP_LIGHTING("_lighting._tcp.local."),
    TCP_TELEVISION("_television._tcp.local."),

    MQTT_MEDIA_PLAYER("/smart_home/mediaplayer"),
    MQTT_TELEVISION("/smart_home/television");

    private final String text;

    ServiceType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static ServiceType fromString(String text) {
        for (ServiceType b : ServiceType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }

        return null;
    }
}
