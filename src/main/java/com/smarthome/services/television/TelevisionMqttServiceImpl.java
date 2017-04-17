package com.smarthome.services.television;

import com.smarthome.services.service.MqttServiceImpl;
import com.smarthome.services.service.ServiceController;

/**
 * Created by Ian C on 17/04/2017.
 */
public class TelevisionMqttServiceImpl extends MqttServiceImpl {

    public TelevisionMqttServiceImpl(ServiceController serviceController) {
        super(serviceController);
    }

    @Override
    public void run() {
        super.run();
    }
}
