package com.smarthome.services.mediaplayer.model;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ian Cunningham
 */
public class MediaPlayerModel extends BaseServiceModel {

    private boolean isMediaPlayerOn;
    private int volume;
    private int currentTrack;
    private PlaylistModel playlist;


    public MediaPlayerModel(String name) {
        super(name, 0);

        playlist = new PlaylistModel();
    }

    public boolean isMediaPlayerOn() {
        return isMediaPlayerOn;
    }

    public void setMediaPlayerOn(boolean mediaPlayerOn) {
        isMediaPlayerOn = mediaPlayerOn;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public PlaylistModel getPlaylist() {
        return playlist;
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public void selectTrack(int code) {
        currentTrack = code;
    }

    @Override
    public Map getValuesMap() {
        Map valuesMap = new HashMap();
        valuesMap.put("Television On", isMediaPlayerOn);
        valuesMap.put("Volume", volume);
        valuesMap.put("Current track", currentTrack);

        return valuesMap;
    }
}
