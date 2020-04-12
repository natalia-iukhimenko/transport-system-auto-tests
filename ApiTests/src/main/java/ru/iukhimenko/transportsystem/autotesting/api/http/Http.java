package ru.iukhimenko.transportsystem.autotesting.api.http;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Http {
    static Logger logger = LoggerFactory.getLogger(Http.class);

    public static HttpResponse<JsonNode> sendGetRequest(String url, Map<String, String> headers, Map<String, Object> requestParams) {
        return Unirest
                .get(url)
                .headers(headers)
                .queryString(requestParams)
                .asJson()
                .ifFailure(response -> {
                    logger.error("Error Code: " + response.getStatus());
                    response.getParsingError().ifPresent(e -> {
                        logger.error("Parsing Exception: ", e);
                        logger.error("Original body: " + e.getOriginalBody());
                    });
                });
    }
    
    public static HttpResponse<JsonNode> sendPostRequest(String url, Map<String, String> headers, Object body) {
        return Unirest
                .post(url)
                .headers(headers)
                .body(body)
                .asJson()
                .ifFailure(response -> {
                    logger.error("Error Code: " + response.getStatus());
                    response.getParsingError().ifPresent(e -> {
                        logger.error("Parsing Exception: ", e);
                        logger.error("Original body: " + e.getOriginalBody());
                    });
                });
    }
}
