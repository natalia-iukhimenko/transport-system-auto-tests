package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportModel;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

import java.util.HashMap;
import java.util.Map;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.*;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class TransportModelService extends ApiService {
    private final User actor;
    private final Logger logger = LoggerFactory.getLogger(TransportModelService.class);

    public TransportModelService(User actor) {
        this.actor = actor;
    }

    public TransportModelService() {
        this.actor = new User(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword());
    }

    public Integer addTransportModel(TransportModel transportModel) {
        Integer createdModelId = -1;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendPostRequest(TRANSPORT_MODELS_ADD_ENDPOINT, headers, transportModel);
        if (response.isSuccess()) {
            try {
                createdModelId = response.getBody().getObject().getInt("id");
                logger.info("A transport model has been added, id = {}", createdModelId);
            }
            catch (JSONException ex) {
                logger.warn(ex.getMessage());
            }
        } else {
            logger.warn("POST {} ended up with status = {} - {}", TRANSPORT_MODELS_ADD_ENDPOINT, response.getStatus(), response.getStatusText());
        }
        return createdModelId;
    }

    public TransportModel getTransportModel(Integer id) {
        TransportModel model = TransportModel.builder().build();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(TRANSPORT_MODEL_ENDPOINT(id), headers);
        if (response.isSuccess()) {
            model = ObjectConverter.convertToObject(response.getBody().getObject(), TransportModel.class);
        } else {
            logger.warn("GET {} ended up with status = {} - {}", TRANSPORT_MODEL_ENDPOINT(id), response.getStatus(), response.getStatusText());
        }
        return model;
    }
}
