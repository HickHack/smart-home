package com.smarthome.services.jacuzzi;

import com.smarthome.services.jacuzzi.model.JacuzziModel;
import com.smarthome.services.lighting.model.LightingModel;
import com.smarthome.services.service.*;
import com.smarthome.services.service.model.BaseServiceModel;
import com.smarthome.services.television.model.TelevisionModel;

import java.util.Map;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service controller. This class holds the logic for
 * handling an operation and manipulating the jacuzzi.
 */
public class JacuzziControllerImpl implements ServiceController {

    private JacuzziModel model;
    private TCPService service;

    public JacuzziControllerImpl(TCPService service) {
        this.service = service;
        model = new JacuzziModel(service.getName(), service.getPort());
    }

    @Override
    public BaseServiceModel performOperation(ServiceOperation operation) {
        switch (operation.getOperationCode()) {
            case 0:
                turnWaterOn();
                break;
            case 1:
                turnWaterOff();
                break;
            case 2:
                turnJetsOn();
                break;
            case 3:
                turnJetsOff();
                break;
            case 4:
                increaseJetPower();
                break;
            case 5:
                decreaseJetPower();
                break;
            default:
                break;

        }
        return model;
    }

    @Override
    public Map getControllerStatus() {
        return model.getValuesMap();
    }

    private void turnWaterOn() {
        if (model.getWaterDepth() == 0 && !model.isWaterRunning()) {
            model.setWaterDepth(80);
            model.setWaterRunning(true);
            service.updateUIOutput("Water is running, depth is 80%");
            turnJetsOn();
            turnLightsOn();
            turnTVOn();
        }
    }

    private void turnLightsOn() {
        BaseServiceModel lightingResult = service.connectToService(new ServiceOperation(0), ServiceType.LIGHTING);

        if (ServiceHelper.isValidResponse(lightingResult, LightingModel.class)) {
            LightingModel lightingModel = (LightingModel) lightingResult;

            if (lightingModel.isLightingOn()) {
                service.updateUIOutput("Successfully Turned Lights On");
            }
        } else {
            service.updateUIOutput("Failed to turn Lights On");
        }
    }

    private void turnTVOn() {
        BaseServiceModel tvResult = service.connectToService(new ServiceOperation(0), ServiceType.TELEVISION);

        if (ServiceHelper.isValidResponse(tvResult, TelevisionModel.class)) {
            TelevisionModel tvModel = (TelevisionModel) tvResult;

            if (tvModel.isTelevisionOn()) {
                service.updateUIOutput("Successfully Turned TV On");
            }
        } else {
            service.updateUIOutput("Failed to turn TV On");
        }
    }

    /**
     * Turn the water off along with the lights and television
     */
    private void turnWaterOff() {
        if (model.isWaterRunning()) {
            model.setWaterDepth(0);
            model.setWaterRunning(false);
            turnJetsOff();
            turnLightsOff();
            turnTVOff();
        }
    }

    private void turnLightsOff() {
        BaseServiceModel lightingResult = service.connectToService(new ServiceOperation(1), ServiceType.LIGHTING);

        if (ServiceHelper.isValidResponse(lightingResult, LightingModel.class)) {
            LightingModel lightingModel = (LightingModel) lightingResult;

            if (!lightingModel.isLightingOn()) {
                service.updateUIOutput("Successfully Turned Lights Off");
            }
        } else {
            service.updateUIOutput("Failed to turn Lights Off");
        }
    }

    private void turnTVOff() {
        BaseServiceModel tvResult = service.connectToService(new ServiceOperation(1), ServiceType.TELEVISION);

        if (ServiceHelper.isValidResponse(tvResult, TelevisionModel.class)) {
            TelevisionModel televisionModel = (TelevisionModel) tvResult;

            if (!televisionModel.isTelevisionOn()) {
                service.updateUIOutput("Successfully Turned TV Off");
            }
        } else {
            service.updateUIOutput("Failed to turn TV Off");
        }
    }

    private void turnJetsOn() {
        if (!model.isJetsRunning() && model.getWaterDepth()> 0) {
            model.setJetPower(40);
            model.setJetsRunning(true);
            service.updateUIOutput("Starting Jets, power is 40%");
        }
    }

    private void turnJetsOff() {
        if (model.isJetsRunning()) {
            model.setJetPower(0);
            model.setJetsRunning(false);
            service.updateUIOutput("Powering jets off");
        }
    }

    private void increaseJetPower() {
        if (model.getJetPower() < 100 && model.isJetsRunning() && model.getWaterDepth() > 0) {
            model.setJetPower(model.getJetPower() + 20);
            service.updateUIOutput("Starting Jets, power is 40%");
        }
    }

    private void decreaseJetPower() {
        if (model.getJetPower() > 0) {
            model.setJetPower(model.getJetPower() - 20);
            service.updateUIOutput("Decreasing jet power");
        }
    }
}
