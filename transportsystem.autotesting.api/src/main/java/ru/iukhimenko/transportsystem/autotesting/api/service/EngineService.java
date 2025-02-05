package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.Engine;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.*;
import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.ENGINES_ENGINE_ENDPOINT;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;


public class EngineService extends ApiService {
    private final Logger logger = LoggerFactory.getLogger(EngineService.class);
    private final User actor;

    public EngineService(User actor) {
        this.actor = actor;
    }

    public EngineService() {
        this.actor = new User(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword());
    }

    public Integer addEngine(Engine engine) {
        Integer engineId = -1;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendPostRequest(ENGINES_ADD_ENDPOINT, headers, engine);
        if (response.isSuccess()) {
            try {
                engineId = response.getBody().getObject().getInt("id");
                logger.info("Engine has been created, id = {}", engineId);
            }
            catch (JSONException ex) {
                logger.warn(ex.getMessage());
            }
        } else {
            logger.warn("POST {} ended up with status = {} - {}", ENGINES_ADD_ENDPOINT, response.getStatus(), response.getStatusText());
        }
        return engineId;
    }

    public void updateEngine(Engine newEngine) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        if (newEngine.getId() != null) {
            HttpResponse<JsonNode> response = Http.sendPutRequest(ENGINES_EDIT_ENDPOINT, headers, newEngine);
            if (response.isSuccess()) {
                logger.info("Engine has been updated, id = {}", newEngine.getId());
            } else {
                logger.warn("PUT {} ended up with status = {} - {}", ENGINES_EDIT_ENDPOINT, response.getStatus(), response.getStatusText());
            }
        }
        else
            logger.warn("Unable to update engine with id = null");
    }

    public Engine getEngine(Integer id) {
        Engine engine = new Engine();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(ENGINES_ENGINE_ENDPOINT(id), headers);
        if (response.isSuccess()) {
            engine = ObjectConverter.convertToObject(response.getBody().getObject(), Engine.class);
        } else {
            logger.warn("GET {} ended up with status = {} - {}", ENGINES_ENGINE_ENDPOINT(id), response.getStatus(), response.getStatusText());
        }
        return engine;
    }

    public List<Engine> getAllEngines() {
        List<Engine> engines = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(ENGINES_ALL_ENDPOINT, headers);
        if (response.isSuccess()) {
            engines = ObjectConverter.convertToObjects(response.getBody().getArray(), Engine.class);
        } else {
            logger.warn("GET {} ended up with status = {} - {}", ENGINES_ALL_ENDPOINT, response.getStatus(), response.getStatusText());
        }
        return engines;
    }

    public void deleteEngine(Integer id) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        Http.sendDeleteRequest(ENGINES_DELETE_ENDPOINT(id), headers);
    }
}
