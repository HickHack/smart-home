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
            turnLightsAndTvOn();
        }
    }

    private void turnLightsAndTvOn() {
        BaseServiceModel lightingResponse = service.connectToService(new ServiceOperation(0), ServiceType.LIGHTING);

        if (ServiceHelper.isValidResponse(lightingResponse, LightingModel.class)) {
            LightingModel lightingModel = (LightingModel) lightingResponse;

            if (lightingModel.isLightingOn()) {
                BaseServiceModel tvResponse = service.connectToService(new ServiceOperation(0), ServiceType.TELEVISION);

                if (ServiceHelper.isValidResponse(tvResponse, TelevisionModel.class)) {
                    System.out.println("Successfully Turned TV and Lights On");
                }
            }
        } else {
            System.out.println("Failed to turn TV and Lights On");
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
            turnLightsAndTvOff();
        }
    }

    private void turnLightsAndTvOff() {
        BaseServiceModel lightingResponse = service.connectToService(new ServiceOperation(1), ServiceType.LIGHTING);

        if (ServiceHelper.isValidResponse(lightingResponse, LightingModel.class)) {
            LightingModel lightingModel = (LightingModel) lightingResponse;

            if (!lightingModel.isLightingOn()) {
                BaseServiceModel tvResponse = service.connectToService(new ServiceOperation(1), ServiceType.TELEVISION);

                if (ServiceHelper.isValidResponse(tvResponse, TelevisionModel.class)) {
                    service.updateUIOutput("Successfully turned TV and lights off");
                }
            }
        } else {
            service.updateUIOutput("Failed to turn lights and TV off");
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
