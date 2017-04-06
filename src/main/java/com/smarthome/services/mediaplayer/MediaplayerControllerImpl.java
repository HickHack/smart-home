package com.smarthome.services.mediaplayer;

import com.smarthome.services.mediaplayer.model.MediaplayerModel;
import com.smarthome.services.service.*;
import com.smarthome.services.television.model.TelevisionModel;

/**
 * Created by Ian C on 06/04/2017.
 */
public class MediaplayerControllerImpl implements ServiceController {

    private MediaplayerModel mpModel;
    private Service service;
    private int volumeLevel;

    public MediaplayerControllerImpl(TCPService service) {
        mpModel = new MediaplayerModel();
        this.service = service;
    }

    @Override
    public ServiceResponse performOperation(ServiceOperation request) {

        switch (request.getOperationCode()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                break;

        }
        return new ServiceResponse(mpModel);    }
}
