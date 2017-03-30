package com.smarthome.services.service;

/**
 * @author Graham Murray
 * @descripion Interface for service controllers
 */
public enum ServiceType {
    JACUZZI("_jacuzzi._tcp.local."),
    LIGHTING("_lighting._tcp.local."),
    TELEVISION("_television._tcp.local."),
    MEDIAPLAYER("_media_player._tcp.local.");

    private final String text;

    /**
     * @param text
     */
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
