package com.smarthome.services.television;

import com.smarthome.services.service.*;
import com.smarthome.services.television.model.TelevisionModel;

/**
 * Created by Ian C on 01/04/2017.
 */
public class TelevisionControllerImpl implements ServiceController {

    private TelevisionModel tvModel;
    private Service service;
    private int volumeLevel;

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
            case 2:
                turnMuteOn();
            case 3:
                turnMuteOff();
            case 4:
                decreaseVolume();
            case 5:
                increaseVolumne();
            case 6:
                decreaseScreenBrightness();
            case 7:
                increaseScreenBrightness();
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