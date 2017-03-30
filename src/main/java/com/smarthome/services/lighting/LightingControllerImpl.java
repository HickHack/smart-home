package com.smarthome.services.lighting;

import com.smarthome.services.lighting.model.LightingModel;
import com.smarthome.services.service.*;

import javax.jmdns.ServiceInfo;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service controller. This class holds the logic for
 * handling an operation and manipulating the jacuzzi.
 */
public class LightingControllerImpl implements ServiceController {

    private LightingModel model;
    private Service service;

    public LightingControllerImpl(TCPService service) {
        this.service = service;
        model = new LightingModel();
    }

    public ServiceResponse performOperation(ServiceOperation request) {
        switch (request.getOperationCode()) {
            case 0:
                turnLightsOn();
                break;
            case 1:
                turnLightsOff();
            case 2:
                increaseBrightness();
            case 3:
                decreaseBrightness();
            default:
                break;

        }

        return new ServiceResponse(model);
    }

    private void turnLightsOn() {
        if (!model.isLightingOn()) {
            model.setLightingOn(true);
            model.setBrightness(40);
        }
    }

    private void turnLightsOff() {
        if (model.isLightingOn()) {
            model.setLightingOn(false);
            model.setBrightness(0);
        }
    }

    private void decreaseBrightness() {
        if (model.isLightingOn() && model.getBrightness() > 0) {


            if (model.getBrightness() - 20 == 0) {
                turnLightsOff();
            }

            model.setBrightness(model.getBrightness() - 20);
        }
    }

    private void increaseBrightness() {
        if (model.getBrightness() < 100) {
            model.setBrightness(model.getBrightness() + 20);
            turnLightsOn();
        }
    }
}
