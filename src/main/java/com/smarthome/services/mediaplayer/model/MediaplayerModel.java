package com.smarthome.services.mediaplayer.model;

import com.smarthome.services.service.model.BaseServiceModel;

import java.util.Map;

/**
 * Created by Ian C on 06/04/2017.
 */
public class MediaplayerModel extends BaseServiceModel {

    private boolean isMediaplayerOn;
    private boolean isMuteOn;
    private int volume;
    private int track;
    private PlaylistModel playlist;

    public MediaplayerModel(String name) {
        super(name, 0);

       // playlist = new Playlist();
    }

    public boolean isMediaplayerOn() {
        return isMediaplayerOn;
    }

    public void setMediaplayerOn(boolean mediaplayerOn) {
        isMediaplayerOn = mediaplayerOn;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public boolean isMuteOn() {
        return isMuteOn;
    }

    public void setMuteOn(boolean muteOn) {
        isMuteOn = muteOn;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public Map getValuesMap() {
        return null;
    }
}
