package com.smarthome.services.lighting;

import com.smarthome.services.lighting.model.LightingModel;
import com.smarthome.services.service.*;
import com.smarthome.services.service.model.BaseServiceModel;

import java.util.Map;

/**
 * @author Graham Murray
 * @descripion Lighting Service controller. This class holds the logic for
 * handling an operation and manipulating the lighting.
 */
public class LightingControllerImpl implements ServiceController {

    private LightingModel model;
    private Service service;

    public LightingControllerImpl(TCPService service) {
        this.service = service;
        model = new LightingModel(service.getName(), service.getPort());
    }

    @Override
    public BaseServiceModel performOperation(ServiceOperation request) {
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

        return model;
    }

    @Override
    public Map getControllerStatus() {
        return model.getValuesMap();
    }

    private void turnLightsOn() {
        if (!model.isLightingOn()) {
            model.setLightingOn(true);
            model.setBrightness(40);
            service.updateUIOutput("Turning lighting On. Brightness: " + model.getBrightness());
        } else {
            service.updateUIOutput("Lighting already On");
        }
    }

    private void turnLightsOff() {
        if (model.isLightingOn()) {
            model.setLightingOn(false);
            model.setBrightness(0);
            service.updateUIOutput("Turning lighting Off");
        } {
            service.updateUIOutput("Lighting already Off");
        }
    }

    private void decreaseBrightness() {
        if (model.isLightingOn() && model.getBrightness() > 0) {


            if (model.getBrightness() - 20 == 0) {
                turnLightsOff();
            }

            model.setBrightness(model.getBrightness() - 20);
            service.updateUIOutput("Decreasing Brightness. Level: " + model.getBrightness());
        }
    }

    private void increaseBrightness() {
        if (model.getBrightness() < 100) {
            model.setBrightness(model.getBrightness() + 20);
            turnLightsOn();
            service.updateUIOutput("Increasing Brightness. Level: " + model.getBrightness());
        } {
            service.updateUIOutput("Brightness is max: Level: " + model.getBrightness());
        }
    }
}
