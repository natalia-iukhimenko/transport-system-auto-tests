package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportDocument;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.*;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class TransportDocumentService extends ApiService {
    private final User actor;
    private final Logger logger = LoggerFactory.getLogger(TransportDocumentService.class);

    public TransportDocumentService() {
        actor = new User(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword());
    }

    public Integer addTransportDocument(TransportDocument transportDocument) {
        Integer createdDocumentId = -1;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendPostRequest(TRANSPORT_DOCUMENTS_ADD_ENDPOINT, headers, transportDocument);
        if (response.isSuccess()) {
            try {
                createdDocumentId = response.getBody().getObject().getInt("id");
                logger.info("A transport document has been added, id = {}", createdDocumentId);
            }
            catch (JSONException ex) {
                logger.warn(ex.getMessage());
            }
        } else {
            logger.warn("POST {} ended up with status = {} - {}", TRANSPORT_DOCUMENTS_ADD_ENDPOINT, response.getStatus(), response.getStatusText());
        }
        return createdDocumentId;
    }

    public List<TransportDocument> getTransportDocuments() {
        List<TransportDocument> documents = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(TRANSPORT_DOCUMENTS_ENDPOINT, headers);
        if (response.isSuccess()) {
            documents = ObjectConverter.convertToObjects(response.getBody().getArray(), TransportDocument.class);
        }  else {
            logger.warn("GET {} ended up with status = {} - {}", TRANSPORT_DOCUMENTS_ENDPOINT, response.getStatus(), response.getStatusText());
        }
        return documents;
    }
}