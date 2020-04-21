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

    public static HttpResponse<JsonNode> sendGetRequest(String url, Map<String, String> headers, Map<String, Object> requestParams) {
        return Unirest
                .get(BASE_URI + url)
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

    public static HttpResponse<JsonNode> sendGetRequest(String url, Map<String, String> headers) {
        return Unirest
                .get(BASE_URI + url)
                .asJson()
                .ifFailure(response -> {
                    logger.error("Error " + response.getStatus());
                    response.getParsingError().ifPresent(e -> {
                        logger.error("Parsing Exception: ", e);
                        logger.error("Original body: " + e.getOriginalBody());
                    });
                });
    }

    public static HttpResponse<JsonNode> sendPostRequest(String url, Map<String, String> headers, Object body) {
        return Unirest
                .post(BASE_URI + url)
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

    public static HttpResponse<JsonNode> sendPostRequest(String url, Object body) {
        return Unirest
                .post(BASE_URI + url)
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
