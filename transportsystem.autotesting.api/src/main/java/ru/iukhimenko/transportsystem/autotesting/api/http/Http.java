package ru.iukhimenko.transportsystem.autotesting.api.http;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.BASE_URI;

public class Http {
    static Logger logger = LoggerFactory.getLogger(Http.class);

    public static HttpResponse<JsonNode> sendGetRequest(String endpoint, Map<String, String> headers, Map<String, Object> requestParams) {
        return Unirest
                .get(BASE_URI + endpoint)
                .headers(headers)
                .queryString(requestParams)
                .asJson()
                .ifFailure(response -> {
                    logger.error("Error " + response.getStatus());
                    response.getParsingError().ifPresent(e -> {
                        logger.error("Parsing Exception: ", e);
                        logger.error("Original body: " + e.getOriginalBody());
                    });
                });
    }

    public static HttpResponse<JsonNode> sendGetRequest(String endpoint, Map<String, String> headers) {
        return Unirest
                .get(BASE_URI + endpoint)
                .asJson()
                .ifFailure(response -> {
                    logger.error("Error " + response.getStatus());
                    response.getParsingError().ifPresent(e -> {
                        logger.error("Parsing Exception: ", e);
                        logger.error("Original body: " + e.getOriginalBody());
                    });
                });
    }

    public static HttpResponse<JsonNode> sendPostRequest(String endpoint, Map<String, String> headers, Object body) {
        return Unirest
                .post(BASE_URI + endpoint)
                .headers(headers)
                .body(body)
                .asJson()
                .ifFailure(response -> {
                    logger.error("Error " + response.getStatus());
                    response.getParsingError().ifPresent(e -> {
                        logger.error("Parsing Exception: ", e);
                        logger.error("Original body: " + e.getOriginalBody());
                    });
                });
    }

    public static HttpResponse<JsonNode> sendPostRequest(String endpoint, Object body) {
        return Unirest
                .post(BASE_URI + endpoint)
                .body(body)
                .asJson()
                .ifFailure(response -> {
                    logger.error("Error " + response.getStatus());
                    response.getParsingError().ifPresent(e -> {
                        logger.error("Parsing Exception: ", e);
                        logger.error("Original body: " + e.getOriginalBody());
                    });
                });
    }

    public static HttpResponse<JsonNode> sendPutRequest(String endpoint, Map<String, String> headers, Object body) {
        return Unirest
                .put(BASE_URI + endpoint)
                .headers(headers)
                .body(body)
                .asJson()
                .ifFailure(response -> {
                    logger.error("Error " + response.getStatus());
                    response.getParsingError().ifPresent(e -> {
                        logger.error("Parsing Exception: ", e);
                        logger.error("Original body: " + e.getOriginalBody());
                    });
                });
    }
}
