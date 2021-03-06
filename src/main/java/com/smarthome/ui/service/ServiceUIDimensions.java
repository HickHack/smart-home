package com.smarthome.ui.service;

/**
 * @author Graham Murray
 */
public class ServiceUIDimensions {

    public static final int STANDARD_X = 5;
    public static final int STANDARD_Y = 5;

    public static final int FRAME_WIDTH = 550;
    public static final int FRAME_HEIGHT = 300;

    public static final int STATUS_AREA_WIDTH = FRAME_WIDTH - (STANDARD_X * 2);
    public static final int STATUS_AREA_HEIGHT = (FRAME_HEIGHT / 4) - (STANDARD_Y * 2);

    public static final int SCROLLPANE_WIDTH = FRAME_WIDTH - (STANDARD_X * 2);
    public static final int SCROLLPANE_HEIGHT = (FRAME_HEIGHT / 4 * 3) - STANDARD_Y;
    public static final int SCROLLPANE_X = STANDARD_X;
    public static final int SCROLLPANE_Y = FRAME_HEIGHT - (FRAME_HEIGHT / 4 * 3);

    public static final int BORDER_DIMENSION = 0;
}
