package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.REGISTER_ENDPOINT;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

public class AuthService {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void registerUser(User user) {
        if (user != null) {
            HttpResponse<JsonNode> response = Http.sendPostRequest(REGISTER_ENDPOINT, user);
            if (response.isSuccess()) {
                logger.info("User {0} has been registered", user.getUsername());
            }
        }
        else
            logger.warn("Registration of null user won't be performed");
    }
}
