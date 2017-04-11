package com.smarthome.services.mediaplayer.model;

import com.smarthome.services.LaunchControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ian C on 07/04/2017.
 */
public class Playlist {

    private List<Integer> tracks;

    public Playlist() {
        tracks = new ArrayList<>();
        populatePlaylist();
    }

    public List getTracks() {
        return tracks;
    }

    public int getTrack(int track) {

        int requestedTrack = 0;

        for(int i =0; i < tracks.size(); i++) {
            requestedTrack = tracks.get(track);
        }

        return requestedTrack;
    }

    public void populatePlaylist() {
        for(int i = 0; i < 20; i++) {
            int track = i + 1;
            tracks.add(track);
            System.out.println(tracks.get(i));
        }
    }

    public int selectRandomTrack() {
        Random random = new Random();

        int track = tracks.get(0) + random.nextInt(tracks.size());

        return track;
    }
}
