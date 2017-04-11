package com.smarthome.services.jacuzzi;

import com.smarthome.services.jacuzzi.model.JacuzziModel;
import com.smarthome.services.service.*;

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
        model = new JacuzziModel();
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation operation) {
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

        return new ServiceResponse(model);
    }

    private void turnWaterOn() {
        if (model.getWaterDepth() == 0 && !model.isWaterRunning()) {
            model.setWaterDepth(80);
            model.setWaterRunning(true);
            turnJetsOn();
            String lightResponse = service.connectToService(new ServiceOperation(0), ServiceType.LIGHTING);
            System.out.println("Jacuzzi Lights: " + lightResponse);
            String televisionResponse = service.connectToService(new ServiceOperation(0), ServiceType.TELEVISION);
            System.out.println("Jacuzzi TV: " + televisionResponse);
        }
    }

    private void turnWaterOff() {
        if (model.isWaterRunning()) {
            model.setWaterDepth(0);
            model.setWaterRunning(false);
            turnJetsOff();
            service.connectToService(new ServiceOperation(1), ServiceType.LIGHTING);
            service.connectToService(new ServiceOperation(1), ServiceType.TELEVISION);
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
