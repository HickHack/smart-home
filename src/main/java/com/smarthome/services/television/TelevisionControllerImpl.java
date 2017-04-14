package com.smarthome.services.television;

import com.smarthome.services.service.*;
import com.smarthome.services.service.model.BaseServiceModel;
import com.smarthome.services.television.model.TelevisionModel;

import java.util.Map;

/**
 * Created by Ian C on 01/04/2017.
 */
public class TelevisionControllerImpl implements ServiceController {

    private TelevisionModel tvModel;
    private Service service;
    private int previousVolumeLevel;

    public TelevisionControllerImpl(TCPService service) {
        tvModel = new TelevisionModel(service.getName(), service.getPort());
        this.service = service;
    }

    @Override
    public BaseServiceModel performOperation(ServiceOperation request) {

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
        return tvModel;
    }

    @Override
    public Map getControllerStatus() {
        return tvModel.getValuesMap();
    }

    private void turnTelevisionOn() {
        if (!tvModel.isTelevisionOn()) {
            tvModel.setTelevisionOn(true);
            tvModel.setVolume(50);
            tvModel.setScreenBrightness(60);
            service.updateUIOutput("Turning TV On. Volume: " + tvModel.getVolume());
        }
    }

    private void turnTelevisionOff() {
        if (tvModel.isTelevisionOn()) {
            tvModel.setTelevisionOn(false);
            tvModel.setVolume(0);
            tvModel.setScreenBrightness(0);
            service.updateUIOutput("Turning TV Off. Volume: " + tvModel.getVolume());
        }
    }

    private void turnMuteOn() {
        if (tvModel.isTelevisionOn() && !tvModel.isMuteOn()) {
            tvModel.setMuteOn(true);
            previousVolumeLevel = tvModel.getVolume();
            tvModel.setVolume(0);
            service.updateUIOutput("Turning Mute On. Volume: " + tvModel.getVolume());
        }
    }

    private void turnMuteOff() {
        if (tvModel.isTelevisionOn() && tvModel.isMuteOn()) {
            tvModel.setMuteOn(false);
            tvModel.setVolume(previousVolumeLevel);
            service.updateUIOutput("Turning Mute Off. Volume: " + tvModel.getVolume());
        }
    }

    private void decreaseVolume() {
        if (tvModel.isTelevisionOn() && tvModel.getVolume() > 0) {

            if (tvModel.getVolume() - 1 == 0) {
                tvModel.setVolume(0);
            }

            tvModel.setVolume(tvModel.getVolume() - 1);
            service.updateUIOutput("Decreasing volume. Volume: " + tvModel.getVolume());
        } else {
            service.updateUIOutput("Cant decrease volume. Volume: " + tvModel.getVolume());
        }

    }

    private void increaseVolumne() {
        if (tvModel.isTelevisionOn() && tvModel.getVolume() < 100) {

            if (tvModel.getVolume() + 1 == 100) {
                tvModel.setVolume(100);
            }

            tvModel.setVolume(tvModel.getVolume() + 1);
            service.updateUIOutput("Increasing volume. Volume" + tvModel.getVolume());
        } else {
            service.updateUIOutput("Cant increase volume. Volume: " + tvModel.getVolume());
        }

    }

    private void decreaseScreenBrightness() {
        if (tvModel.isTelevisionOn() && tvModel.getScreenBrightness() > 0) {

            if (tvModel.getScreenBrightness() - 20 == 0) {
                tvModel.setScreenBrightness(0);
            }

            tvModel.setScreenBrightness(tvModel.getScreenBrightness() - 20);
            service.updateUIOutput("Decreasing brightness. Brightness: " + tvModel.getVolume());
        } else {
            service.updateUIOutput("Cant decrease brightness: Brightness: " + tvModel.getScreenBrightness());
        }
    }

    private void increaseScreenBrightness() {
        if (tvModel.isTelevisionOn() && tvModel.getScreenBrightness() < 100) {

            if (tvModel.getScreenBrightness() + 20 >= 100) {
                tvModel.setScreenBrightness(100);
            }

            tvModel.setScreenBrightness(tvModel.getScreenBrightness() + 20);
            service.updateUIOutput("Increasing brightness. Brightness: " + tvModel.getVolume());
        } else {
            service.updateUIOutput("Cant increase brightness. Brightness: " + tvModel.getVolume());
        }
    }
}
