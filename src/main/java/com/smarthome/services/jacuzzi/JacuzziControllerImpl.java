package com.smarthome.services.jacuzzi;

import com.smarthome.services.jacuzzi.model.JacuzziModel;
import com.smarthome.services.lighting.model.LightingModel;
import com.smarthome.services.service.*;
import com.smarthome.services.service.model.BaseServiceModel;
import com.smarthome.services.television.model.TelevisionModel;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Pack200;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service controller. This class holds the logic for
 * handling an operation and manipulating the jacuzzi.
 */
public class JacuzziControllerImpl implements ServiceController {

    private JacuzziModel model;
    private TCPService service;
    private Timer timer;

    public JacuzziControllerImpl(TCPService service) {
        this.service = service;
        model = new JacuzziModel(service.getName(), service.getPort());
        timer = new Timer();
    }

    @Override
    public BaseServiceModel performOperation(ServiceOperation operation) {
        switch (operation.getOperationCode()) {
            case 0:
                turnOn();
                break;
            case 1:
                turnOff();
                break;
            case 3:
                increaseJetPower();
                break;
            case 4:
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

    private void turnOn() {
        if (model.getWaterDepth() == 0 && !model.isWaterRunning()) {
            model.setWaterDepth(0);
            model.setWaterRunning(true);
            service.updateUIOutput("Water is running, depth is 1%");
            timer.schedule(new WaterTask(), 0, 1000);
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

    private void increaseLightBrightness() {
        BaseServiceModel lightingResult = service.connectToService(new ServiceOperation(2), ServiceType.LIGHTING);

        if (!ServiceHelper.isValidResponse(lightingResult, LightingModel.class)) {
            service.updateUIOutput("Successfully to increased lights");
        } else {
            service.updateUIOutput("Failed to increase lights");
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
    private void turnOff() {
        if (model.isWaterRunning()) {
            model.setWaterDepth(0);
            model.setWaterRunning(false);
            turnJetsOff();
            turnLightsOff();
            turnTVOff();
            timer.cancel();
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
            model.setJetPower(10);
            model.setJetsRunning(true);
            service.updateUIOutput("Starting Jets, power is 10%");
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
        if (model.getJetPower() < 100 && model.getWaterDepth() > 0) {
            model.setJetPower(model.getJetPower() + 5);
            service.updateUIOutput("Increasing jet power");
        }
    }

    private void decreaseJetPower() {
        if (model.getJetPower() > 0) {
            model.setJetPower(model.getJetPower() - 5);
            service.updateUIOutput("Decreasing jet power");
        }
    }

    class WaterTask extends TimerTask {

        @Override
        public void run() {
            if (model.getWaterDepth() < 100) {
                model.setWaterDepth(model.getWaterDepth() + 4);
                increaseJetPower();
                increaseLightBrightness();
                service.updateUIStatus();
            } else {
                model.setWaterRunning(false);
                timer.cancel();
                service.updateUIOutput("Water full.");
            }
        }
    }
}
