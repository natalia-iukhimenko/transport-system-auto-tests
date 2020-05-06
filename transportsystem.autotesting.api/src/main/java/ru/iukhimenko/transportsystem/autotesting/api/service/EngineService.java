package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.ILogMessageHelper;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.Engine;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

import java.util.HashMap;
import java.util.Map;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.*;

public class EngineService implements ILogMessageHelper {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    private User actor;

    public EngineService(User actor) {
        this.actor = actor;
    }

    public Integer addEngine(Engine engine) {
        Integer engineId = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendPostRequest(ENGINES_ADD_ENDPOINT, headers, engine);
        if (response.isSuccess()) {
            logger.info(getRequestExecutionResultMessage(ENGINES_ADD_ENDPOINT, response.getStatus()));
            try {
                engineId = response.getBody().getObject().getInt("id");
                logger.info("Engine has been created, id = " + engineId);
            }
            catch (JSONException ex) {
                logger.warn(ex.getMessage());
            }
        }
        else
            logger.info(response.getBody().getObject().toString());
        return engineId;
    }

    public void updateEngine(Engine newEngine) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        if (newEngine.getId() != null) {
            HttpResponse<JsonNode> response = Http.sendPutRequest(ENGINES_EDIT_ENDPOINT, headers, newEngine);
            if (response.isSuccess()) {
                logger.info(getRequestExecutionResultMessage(ENGINES_EDIT_ENDPOINT, response.getStatus()));
                logger.info("Engine has been updated, id = " + newEngine.getId());
            } else
                logger.info(response.getBody().getObject().toString());
        }
        else
            logger.warn("Unable to update engine with id = null");
    }

    public Engine getEngine(Integer id) {
        Engine engine = null;
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(ENGINES_ENGINE_ENDPOINT(id), headers);
        if (response.isSuccess()) {
            engine = ObjectConverter.convertToObject(response.getBody().getObject(), Engine.class);
            if (engine == null)
                logger.warn("Failed to map engine");
            logger.info(getRequestExecutionResultMessage(ENGINES_ENGINE_ENDPOINT(id), response.getStatus()));
        }
        else
            logger.info(response.getBody().getObject().toString());
        return engine;
    }
}
