package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.*;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.*;

import ru.iukhimenko.transportsystem.autotesting.api.ILogMessageHelper;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthService implements ILogMessageHelper {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void registerUser(User user) {
        if (user != null) {
            HttpResponse<JsonNode> response = Http.sendPostRequest(REGISTER_ENDPOINT, user);
            logger.info(getRequestExecutionResultMessage(REGISTER_ENDPOINT, response.getStatus(), response.getStatusText()));
        } else
            logger.info("Registration of null user cannot be performed");
    }

    public User authenticateUser(User user) {
        User authenticatedUser = null;
        if (user != null) {
            HttpResponse<JsonNode> response = Http.sendPostRequest(AUTH_ENDPOINT, user);
            if (response.isSuccess()) {
                authenticatedUser = ObjectConverter.convertToObject(response.getBody().getObject(), User.class);
                if (authenticatedUser == null)
                    logger.warn("Failed to map user after authentication");
            }
            logger.info(getRequestExecutionResultMessage(AUTH_ENDPOINT, response.getStatus(), response.getStatusText()));
        } else
            logger.info("Authentication of null user cannot be performed");
        return authenticatedUser;
    }

    public List<User> getUsers() {
        List<User> users = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAccessToken(new User(ADMIN_USERNAME, ADMIN_PASSWORD)));
        HttpResponse<JsonNode> response = Http.sendGetRequest(USERS_ENDPOINT, headers);
        if (response.isSuccess()) {
            users = ObjectConverter.convertToObjects(response.getBody().getArray(), User.class);
            if (users == null)
                logger.warn("Failed to map users");
        }
        logger.info(getRequestExecutionResultMessage(USERS_ENDPOINT, response.getStatus(), response.getStatusText()));
        return users;
    }

    public String getAccessToken(User user) {
        String accessToken = null;
        HttpResponse<JsonNode> response = Http.sendPostRequest(AUTH_ENDPOINT, user);
        if (response.isSuccess()) {
            String type = response.getBody().getObject().getString("type");
            String token = response.getBody().getObject().getString("token");
            if (StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(token)) {
                accessToken = type + " " + token;
            }
        }
        logger.info(getRequestExecutionResultMessage(AUTH_ENDPOINT, response.getStatus(), response.getStatusText()));
        return accessToken;
    }
}
