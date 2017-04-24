package com.smarthome.ui.client;

/**
 * @author Graham Murray
 */
public class ClientUIDimensions {

    public static final int STANDARD_X = 5;
    public static final int STANDARD_Y = 5;

    public static final int FRAME_WIDTH = 200;
    public static final int FRAME_HEIGHT = 300;

    public static  final int TITLE_WIDTH = FRAME_WIDTH;
    public static  final int TITLE_HEIGHT = 20;

    public static final int BUTTON_HEIGHT = 35;
    public static final int BUTTON_WIDTH = 150;

    public static final int BUTTON_CENTER_X = (FRAME_WIDTH - BUTTON_WIDTH) / 2;
    public static final int LAUNCH_BUTTON_Y = TITLE_HEIGHT * 3;

    public static final int JACUZZI_BUTTON_Y = LAUNCH_BUTTON_Y * 2;
    public static final int LIGHTING_BUTTON_Y = JACUZZI_BUTTON_Y + BUTTON_HEIGHT;
    public static final int TELEVISION_BUTTON_Y = LIGHTING_BUTTON_Y + BUTTON_HEIGHT;
    public static final int MEDIA_PLAYER_BUTTON_Y = TELEVISION_BUTTON_Y + BUTTON_HEIGHT;
}
