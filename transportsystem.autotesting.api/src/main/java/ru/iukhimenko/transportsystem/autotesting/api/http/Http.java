package ru.iukhimenko.transportsystem.autotesting.api.http;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

import static ru.iukhimenko.transportsystem.autotesting.core.Configs.BASE_URI;
import static ru.iukhimenko.transportsystem.autotesting.core.LogMessageTemplate.*;

public class Http {
    private static Logger logger = LoggerFactory.getLogger(Http.class);

    public static HttpResponse<JsonNode> sendGetRequest(String endpoint, Map<String, String> headers, Map<String, Object> requestParams) {
        return Unirest
                .get(BASE_URI + endpoint)
                .headers(headers)
                .queryString(requestParams)
                .asJson()
                .ifSuccess(response -> logger.info(getHttpMessageText(endpoint, response.getStatus())))
                .ifFailure(response -> logger.error(getHttpErrorMessageText(endpoint, response.getStatus(), response.getBody().toString())));
    }

    public static HttpResponse<JsonNode> sendGetRequest(String endpoint, Map<String, String> headers) {
        return Unirest
                .get(BASE_URI + endpoint)
                .headers(headers)
                .asJson()
                .ifSuccess(response -> logger.info(getHttpMessageText(endpoint, response.getStatus())))
                .ifFailure(response -> logger.error(getHttpErrorMessageText(endpoint, response.getStatus(), response.getBody().toString())));
    }

    public static HttpResponse<JsonNode> sendPostRequest(String endpoint, Map<String, String> headers, Object body) {
        return Unirest
                .post(BASE_URI + endpoint)
                .headers(headers)
                .body(body)
                .asJson()
                .ifSuccess(response -> logger.info(getHttpMessageText(endpoint, response.getStatus())))
                .ifFailure(response -> logger.error(getHttpErrorMessageText(endpoint, response.getStatus(), response.getBody().toString())));
    }

    public static HttpResponse<JsonNode> sendPostRequest(String endpoint, Object body) {
        return Unirest
                .post(BASE_URI + endpoint)
                .body(body)
                .asJson()
                .ifSuccess(response -> logger.info(getHttpMessageText(endpoint, response.getStatus())))
                .ifFailure(response -> logger.error(getHttpErrorMessageText(endpoint, response.getStatus(), response.getBody().toString())));
    }

    public static HttpResponse<JsonNode> sendPutRequest(String endpoint, Map<String, String> headers, Object body) {
        return Unirest
                .put(BASE_URI + endpoint)
                .headers(headers)
                .body(body)
                .asJson()
                .ifSuccess(response -> logger.info(getHttpMessageText(endpoint, response.getStatus())))
                .ifFailure(response -> logger.error(getHttpErrorMessageText(endpoint, response.getStatus(), response.getBody().toString())));
    }

    public static HttpResponse<JsonNode> sendDeleteRequest(String endpoint, Map<String, String> headers) {
        return Unirest
                .delete(BASE_URI + endpoint)
                .headers(headers)
                .asJson()
                .ifSuccess(response -> logger.info(getHttpMessageText(endpoint, response.getStatus())))
                .ifFailure(response -> logger.error(getHttpErrorMessageText(endpoint, response.getStatus(), response.getBody().toString())));
    }
}
