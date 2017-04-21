package com.smarthome.services.jacuzzi;

import com.smarthome.services.jacuzzi.model.JacuzziModel;
import com.smarthome.services.service.*;
import com.smarthome.services.service.ServiceType;
import com.smarthome.services.service.tcp.TCPService;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.smarthome.services.service.ServiceResponse.Status;

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
    public ServiceResponse performOperation(ServiceOperation operation) {
        Status status;
        switch (operation.getOperationCode()) {
            case 0:
                status = turnOn();
                break;
            case 1:
                status = turnOff();
                break;
            case 3:
                status = increaseJetPower();
                break;
            case 4:
                status = decreaseJetPower();
                break;
            default:
                status = Status.UNSUPPORTED_OPERATION;
                break;
        }

        return new ServiceResponse(status, model, service.getType());
    }

    @Override
    public Map getControllerStatus() {
        return model.getValuesMap();
    }

    private Status turnOn() {
        if (model.getWaterDepth() == 0 && !model.isWaterRunning()) {
            model.setWaterDepth(0);
            model.setWaterRunning(true);
            service.updateUIOutput("Water is running, depth is 1%");
            turnJetsOn();
            turnLightsOn();
            turnTVOn();
            timer.schedule(new WaterTask(), 0, 2000);

            return Status.OK;
        } else {
            return Status.FAILED;
        }
    }

    private void turnLightsOn() {
        ServiceResponse response = service.connectToService(new ServiceOperation(0), ServiceType.TCP_LIGHTING);

        if (ServiceHelper.isValidResponse(response)) {
            service.updateUIOutput("Successfully Turned Lights On");
        } else {
            service.updateUIOutput("Failed to turn Lights On");
        }
    }

    private void increaseLightBrightness() {
        ServiceResponse response = service.connectToService(new ServiceOperation(2), ServiceType.TCP_LIGHTING);

        if (ServiceHelper.isValidResponse(response)) {
            service.updateUIOutput("Successfully to increased lights");
        } else {
            service.updateUIOutput("Failed to increase lights");
        }
    }

    private void turnTVOn() {
        ServiceResponse response = service.connectToService(new ServiceOperation(0), ServiceType.TCP_TELEVISION);

        if (ServiceHelper.isValidResponse(response)) {
            service.updateUIOutput("Successfully Turned TV On");
        } else {
            service.updateUIOutput("Failed to turn TV On");
        }
    }

    /**
     * Turn the water off along with the lights and television
     */
    private Status turnOff() {
        if (model.isWaterRunning()) {
            model.setWaterDepth(0);
            model.setWaterRunning(false);
            turnJetsOff();
            turnLightsOff();
            turnTVOff();
            timer.cancel();

            return Status.OK;
        }

        return Status.FAILED;
    }

    private void turnLightsOff() {
        ServiceResponse response = service.connectToService(new ServiceOperation(1), ServiceType.TCP_LIGHTING);

        if (ServiceHelper.isValidResponse(response)) {
            service.updateUIOutput("Successfully Turned Lights Off");
        } else {
            service.updateUIOutput("Failed to turn Lights Off");
        }
    }

    private void turnTVOff() {
        ServiceResponse response = service.connectToService(new ServiceOperation(1), ServiceType.TCP_TELEVISION);

        if (ServiceHelper.isValidResponse(response)) {
            service.updateUIOutput("Successfully Turned TV Off");
        } else {
            service.updateUIOutput("Failed to turn TV Off");
        }
    }

    private void turnJetsOn() {
        if (!model.isJetsRunning()) {
            model.setJetPower(0);
            model.setJetsRunning(true);
            service.updateUIOutput("Starting Jets, power is 0%");
        }
    }

    private void turnJetsOff() {
        if (model.isJetsRunning()) {
            model.setJetPower(0);
            model.setJetsRunning(false);
            service.updateUIOutput("Powering jets off");
        }
    }

    private Status increaseJetPower() {
        if (model.getJetPower() < 100 && model.getWaterDepth() > 0) {
            model.setJetPower(model.getJetPower() + 5);
            service.updateUIOutput("Increasing jet power");
            return Status.OK;
        }

        return Status.FAILED;
    }

    private Status decreaseJetPower() {
        if (model.getJetPower() > 0) {
            model.setJetPower(model.getJetPower() - 5);
            service.updateUIOutput("Decreasing jet power");

            return Status.OK;
        }

        return Status.FAILED;
    }

    class WaterTask extends TimerTask {

        @Override
        public void run() {
            if (model.getWaterDepth() < 100) {
                model.setWaterDepth(model.getWaterDepth() + 4);
                increaseJetPower();
                increaseLightBrightness();
               // decreaseTvVolume();
                service.updateUIStatus();
                service.updateUIOutput("Filling water.");
            } else {
                model.setWaterRunning(false);
                timer.cancel();
                service.updateUIOutput("Water full.");
            }
        }
    }
}
