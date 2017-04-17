package com.smarthome.services.television;

import com.smarthome.services.service.*;

/**
 * Created by Ian C on 01/04/2017.
 */
public class TelevisionServiceImpl extends TCPServiceImpl{

    public TelevisionServiceImpl(String name) {
        super(name, ServiceType.TELEVISION);
    }

    @Override
    public void run() {
        setController(new TelevisionControllerImpl(this));
        start();
    }
}
