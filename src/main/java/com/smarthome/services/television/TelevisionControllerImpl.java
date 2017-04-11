package com.smarthome.services.television;

import com.google.gson.Gson;
import com.smarthome.services.mediaplayer.MediaPlayerServiceImpl;
import com.smarthome.services.service.*;
import com.smarthome.services.television.model.TelevisionModel;

/**
 * Created by Ian C on 01/04/2017.
 */
public class TelevisionControllerImpl implements ServiceController {

    private TelevisionModel tvModel;
    private Service service;
    MediaPlayerServiceImpl mp;
    private int volumeLevel;

    MediaPlayerServiceImpl mediaPlayerService;
    ServiceServer server;
    Gson gson;

    public TelevisionControllerImpl(TCPService service) {
        tvModel = new TelevisionModel();
        this.service = service;
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation request) {

        switch (request.getOperationCode()) {
            case 0:
                turnTelevisionOn();
                break;
            case 1:
                turnTelevisionOff();
                break;
            case 2:
                turnMuteOn();
                break;
            case 3:
                turnMuteOff();
                break;
            case 4:
                decreaseVolume();
                break;
            case 5:
                increaseVolumne();
                break;
            case 6:
                decreaseScreenBrightness();
                break;
            case 7:
                increaseScreenBrightness();
                break;
            default:
                break;

        }
        return new ServiceResponse(tvModel);
    }

    private void turnTelevisionOn() {
        if (!tvModel.isTelevisionOn()) {
            tvModel.setTelevisionOn(true);
            tvModel.setVolume(50);
            tvModel.setScreenBrightness(60);
        }
    }

    private void turnTelevisionOff() {
        if (tvModel.isTelevisionOn()) {
            tvModel.setTelevisionOn(false);
            tvModel.setVolume(0);
            tvModel.setScreenBrightness(0);
        }
    }

    private void turnMuteOn() {
        if (tvModel.isTelevisionOn() && !tvModel.isMuteOn()) {
            tvModel.setMuteOn(true);
            volumeLevel = tvModel.getVolume();
            tvModel.setVolume(0);
        }
    }

    private void turnMuteOff() {
        if (tvModel.isTelevisionOn() && tvModel.isMuteOn()) {
            tvModel.setMuteOn(false);
            tvModel.setVolume(volumeLevel);
        }
    }

    private void decreaseVolume() {
        if (tvModel.isTelevisionOn() && tvModel.getVolume() > 0) {

            if (tvModel.getVolume() - 1 == 0) {
                tvModel.setVolume(0);
            }

            tvModel.setVolume(tvModel.getVolume() - 1);
        }

    }

    private void increaseVolumne() {
        if (tvModel.isTelevisionOn() && tvModel.getVolume() < 100) {

            if (tvModel.getVolume() + 1 == 100) {
                tvModel.setVolume(100);
            }

            tvModel.setVolume(tvModel.getVolume() + 1);
        }

    }

    private void decreaseScreenBrightness() {
        if (tvModel.isTelevisionOn() && tvModel.getScreenBrightness() > 0) {

            if (tvModel.getScreenBrightness() - 20 == 0) {
                tvModel.setScreenBrightness(0);
            }

            tvModel.setScreenBrightness(tvModel.getScreenBrightness() - 20);
        }
    }

    private void increaseScreenBrightness() {
        if (tvModel.isTelevisionOn() && tvModel.getScreenBrightness() < 100) {

            if (tvModel.getScreenBrightness() + 20 >= 100) {
                tvModel.setScreenBrightness(100);
            }

            tvModel.setScreenBrightness(tvModel.getScreenBrightness() + 20);
        }
    }
}
