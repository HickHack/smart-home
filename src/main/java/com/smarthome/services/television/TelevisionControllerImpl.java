package com.smarthome.services.television;

import com.smarthome.services.service.*;
import com.smarthome.services.television.model.TelevisionModel;

import java.util.Map;

import static com.smarthome.services.service.ServiceResponse.Status;

/**
 * @author Ian Cunningham
 */
public class TelevisionControllerImpl implements ServiceController {

    private TelevisionModel model;
    private Service service;

    public TelevisionControllerImpl(TCPService service) {
        model = new TelevisionModel(service.getName(), service.getPort());
        this.service = service;
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation request) {
        Status status;

        switch (request.getOperationCode()) {
            case 0:
                status = turnTelevisionOn();
                break;
            case 1:
                status = turnTelevisionOff();
                break;
            case 4:
                status = decreaseVolume();
                break;
            case 5:
                status = increaseVolume();
                break;
            default:
                status = Status.UNSUPPORTED_OPERATION;
                break;

        }

        return new ServiceResponse(status, model);
    }

    @Override
    public Map getControllerStatus() {
        return model.getValuesMap();
    }

    private Status turnTelevisionOn() {
        if (!model.isTelevisionOn()) {
            model.setTelevisionOn(true);
            model.setVolume(50);
            model.setScreenBrightness(60);
            service.updateUIOutput("Turning TV On. Volume: " + model.getVolume());

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status turnTelevisionOff() {
        if (model.isTelevisionOn()) {
            model.setTelevisionOn(false);
            model.setVolume(0);
            model.setScreenBrightness(0);
            service.updateUIOutput("Turning TV Off. Volume: " + model.getVolume());

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status decreaseVolume() {
        if (model.isTelevisionOn() && model.getVolume() > 0) {

            if (model.getVolume() - 1 == 0) {
                model.setVolume(0);
            }

            model.setVolume(model.getVolume() - 1);
            service.updateUIOutput("Decreasing volume. Volume: " + model.getVolume());

            return Status.OK;
        }

        service.updateUIOutput("Cant decrease volume. Volume: " + model.getVolume());
        return Status.FAILED;
    }

    private Status increaseVolume() {
        if (model.isTelevisionOn() && model.getVolume() < 100) {

            if (model.getVolume() + 1 == 100) {
                model.setVolume(100);
            }

            model.setVolume(model.getVolume() + 1);
            service.updateUIOutput("Increasing volume. Volume" + model.getVolume());

            return Status.OK;
        }

        service.updateUIOutput("Cant increase volume. Volume: " + model.getVolume());
        return Status.FAILED;
    }
}
