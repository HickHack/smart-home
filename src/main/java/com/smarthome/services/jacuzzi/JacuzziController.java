package com.smarthome.services.jacuzzi;

import com.smarthome.services.model.JacuzziModel;
import com.smarthome.services.model.ServiceRequestModel;
import com.smarthome.services.model.ServiceResponseModel;

/**
 * @author Graham Murray
 * @descripion Jacuzzi service controller. This class holds the logic for
 * handling an operation and manipulating the jacuzzi.
 */
public class JacuzziController {

    private JacuzziModel model;

    public JacuzziController() {
        model = new JacuzziModel();
    }

    public ServiceResponseModel performOperation(ServiceRequestModel request) {
        switch (request.getOperation()) {
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

        return new ServiceResponseModel(model);
    }

    private void turnWaterOn() {
        if (model.getWaterDepth() == 0 && !model.isWaterRunning()) {
            model.setWaterDepth(80);
            model.setWaterRunning(true);
            turnJetsOn();
        }
    }

    private void turnWaterOff() {
        if (model.isWaterRunning()) {
            model.setWaterDepth(0);
            model.setWaterRunning(false);
            turnJetsOff();
        }
    }

    private void turnJetsOn() {
        if (!model.isJetsRunning() && model.getWaterDepth()> 0) {
            model.setJetPower(40);
            model.setJetsRunning(true);
        }
    }

    private void turnJetsOff() {
        if (model.isJetsRunning()) {
            model.setJetPower(0);
            model.setJetsRunning(false);
        }
    }

    private void increaseJetPower() {
        if (model.getJetPower() < 100 && model.isJetsRunning() && model.getWaterDepth() > 0) {
            model.setJetPower(model.getJetPower() + 20);
        }
    }

    private void decreaseJetPower() {
        if (model.getJetPower() > 0) {
            model.setJetPower(model.getJetPower() - 20);
        }
    }
}
