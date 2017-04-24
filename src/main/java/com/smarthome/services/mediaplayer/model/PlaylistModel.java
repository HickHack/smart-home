package com.smarthome.services.mediaplayer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ian Cunningham
 */
public class PlaylistModel {

    private List<Integer> tracks;

    public PlaylistModel() {
        tracks = new ArrayList<>();
        populatePlaylist();
    }

    public void populatePlaylist() {
        for(int i = 4; i < 11; i++) {
            int track = i + 1;
            tracks.add(track);
        }
    }

    public List getTracks() {
        return tracks;
    }
}
