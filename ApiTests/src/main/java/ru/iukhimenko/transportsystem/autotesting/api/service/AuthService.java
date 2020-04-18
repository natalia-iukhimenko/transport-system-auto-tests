package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.AUTH_ENDPOINT;
import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.REGISTER_ENDPOINT;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

public class AuthService {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void registerUser(User user) {
        if (user != null) {
            HttpResponse<JsonNode> response = Http.sendPostRequest(REGISTER_ENDPOINT, user);
            if (response.isSuccess()) {
                logger.info("User " + user.getUsername() + " has been registered");
            }
        }
        else
            logger.warn("Registration of null user cannot be performed");
    }

    public User authenticateUser(User user) {
        User authenticatedUser = null;
        if (user != null) {
            HttpResponse<JsonNode> response = Http.sendPostRequest(AUTH_ENDPOINT, user);
            if (response.isSuccess()) {
                authenticatedUser = ObjectConverter.convertToObject(response.getBody().toString(), User.class);
                logger.info("User " + user.getUsername() + " has been authenticated");
            }
        } else
            logger.warn("Authentication of null user cannot be performed");
        return authenticatedUser;
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
