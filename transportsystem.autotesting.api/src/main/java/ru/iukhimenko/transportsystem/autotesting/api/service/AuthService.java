package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.*;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class AuthService extends ApiService {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    public boolean registerUser(User user) {
        HttpResponse<JsonNode> response = Http.sendPostRequest(REGISTER_ENDPOINT, user);
        return response.isSuccess();
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
        } else
            logger.info("Authentication of null user cannot be performed");
        return authenticatedUser;
    }

    public List<User> getUsers() {
        List<User> users = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAccessToken(new User(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword())));
        HttpResponse<JsonNode> response = Http.sendGetRequest(USERS_ENDPOINT, headers);
        if (response.isSuccess()) {
            users = ObjectConverter.convertToObjects(response.getBody().getArray(), User.class);
            if (users == null)
                logger.warn("Failed to map users");
        }
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
        return accessToken;
    }
}
