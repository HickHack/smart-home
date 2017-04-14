package com.smarthome.services.lighting;

import com.smarthome.services.lighting.model.LightingModel;
import com.smarthome.services.service.*;
import com.smarthome.services.service.model.BaseServiceModel;

import java.util.Map;

import static com.smarthome.services.service.ServiceResponse.Status;

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
    public ServiceResponse performOperation(ServiceOperation request) {
        Status status;

        switch (request.getOperationCode()) {
            case 0:
                status = turnLightsOn();
                break;
            case 1:
                status = turnLightsOff();
                break;
            case 2:
                status = increaseBrightness();
                break;
            case 3:
                status = decreaseBrightness();
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

    private Status turnLightsOn() {
        if (!model.isLightingOn()) {
            model.setLightingOn(true);
            model.setBrightness(40);
            service.updateUIOutput("Turning lighting On. Brightness: " + model.getBrightness());

            return Status.OK;
        } else {
            service.updateUIOutput("Lighting already On");
        }

        return Status.FAILED;
    }

    private Status turnLightsOff() {
        if (model.isLightingOn()) {
            model.setLightingOn(false);
            model.setBrightness(0);
            service.updateUIOutput("Turning lighting Off");

            return Status.OK;
        } else {
            service.updateUIOutput("Lighting already Off");
        }

        return Status.FAILED;
    }

    private Status decreaseBrightness() {
        if (model.isLightingOn() && model.getBrightness() > 0) {


            if (model.getBrightness() - 20 == 0) {
                turnLightsOff();
            }

            model.setBrightness(model.getBrightness() - 20);
            service.updateUIOutput("Decreasing Brightness. Level: " + model.getBrightness());

            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status increaseBrightness() {
        if (model.getBrightness() < 100) {
            model.setBrightness(model.getBrightness() + 20);
            turnLightsOn();
            service.updateUIOutput("Increasing Brightness. Level: " + model.getBrightness());

            return Status.OK;
        } else {
            service.updateUIOutput("Brightness is max: Level: " + model.getBrightness());
        }

        return Status.FAILED;
    }
}
