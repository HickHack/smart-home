package com.smarthome.services.service.model.deserialize;

import com.google.gson.*;
import com.smarthome.services.jacuzzi.model.JacuzziModel;
import com.smarthome.services.lighting.model.LightingModel;
import com.smarthome.services.mediaplayer.model.MediaPlayerModel;
import com.smarthome.services.service.ServiceResponse;
import com.smarthome.services.service.model.BaseServiceModel;
import com.smarthome.services.television.model.TelevisionModel;

import java.lang.reflect.Type;

/**
 * @author Graham Murray
 * @description Custom Gson deserializer used to deserialize service
 * models in a service response
 */
public class ServiceResponseDeserializer implements JsonDeserializer<ServiceResponse> {

    private Gson gson;

    public ServiceResponseDeserializer() {
        gson = new Gson();
    }

    @Override
    public ServiceResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ServiceResponse response = new Gson().fromJson(json, ServiceResponse.class);
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("model")) {
            JsonElement modelElem = jsonObject.get("model");

            if (modelElem != null && !modelElem.isJsonNull()) {
                String modelString = modelElem.toString();
                BaseServiceModel model;

                switch (response.getType()) {
                    case TCP_JACUZZI:
                        model = gson.fromJson(modelString, JacuzziModel.class);
                        break;
                    case TCP_LIGHTING:
                        model =  gson.fromJson(modelString, LightingModel.class);
                        break;
                    case TCP_TELEVISION:
                        model = gson.fromJson(modelString, TelevisionModel.class);
                        break;
                    case MQTT_MEDIA_PLAYER:
                        model = gson.fromJson(modelString, MediaPlayerModel.class);
                        break;
                    default:
                        model = gson.fromJson(modelString, BaseServiceModel.class);
                        break;
                }

                response.setModel(model);
            }
        }

        return response;
    }
}

