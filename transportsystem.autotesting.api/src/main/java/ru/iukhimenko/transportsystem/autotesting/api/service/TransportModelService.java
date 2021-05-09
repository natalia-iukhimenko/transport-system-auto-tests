package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportModel;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.TRANSPORT_MODELS_ADD_ENDPOINT;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class TransportModelService extends ApiService {
    private User actor;
    private Logger logger = LoggerFactory.getLogger(TransportModelService.class);

    public TransportModelService(User actor) {
        this.actor = actor;
    }

    public TransportModelService() {
        this.actor = new User(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword());
    }

    public Integer addTransportModel(TransportModel transportModel) {
        Integer createdModelId = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendPostRequest(TRANSPORT_MODELS_ADD_ENDPOINT, headers, transportModel);
        if (response.isSuccess()) {
            try {
                createdModelId = response.getBody().getObject().getInt("id");
                logger.info("A transport model has been added, id = " + createdModelId);
            }
            catch (JSONException ex) {
                logger.warn(ex.getMessage());
            }
        }
        return createdModelId;
    }

    public TransportModel getTransportModel(Integer id) {
        TransportModel model = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(AppEndpoints.TRANSPORT_MODEL_ENDPOINT(id), headers);
        if (response.isSuccess()) {
            model = ObjectConverter.convertToObject(response.getBody().getObject(), TransportModel.class);
            if (model == null)
                logger.warn("Failed to map transport model");
        }
        return model;
    }

    public List<TransportModel> getTransportModels() {
        List<TransportModel> models = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(AppEndpoints.TRANSPORT_MODELS_ENDPOINT, headers);
        if (response.isSuccess()) {
            models = ObjectConverter.convertToObjects(response.getBody().getArray(), TransportModel.class);
            if (models == null)
                logger.warn("Failed to map transport models");
        }
        return models;
    }
}
