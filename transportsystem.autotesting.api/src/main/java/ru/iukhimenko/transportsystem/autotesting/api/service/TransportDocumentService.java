package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.TRANSPORT_DOCUMENTS_ENDPOINT;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportDocument;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.TRANSPORT_DOCUMENTS_ADD_ENDPOINT;

public class TransportDocumentService extends ApiService {
    private User actor;
    private Logger logger = LoggerFactory.getLogger(TransportDocumentService.class);

    public TransportDocumentService() {
        super();
        actor = new User(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    public TransportDocumentService(User actor) {
        super();
        this.actor = actor;
    }

    public Integer addTransportDocument(TransportDocument transportDocument) {
        Integer createdDocumentId = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendPostRequest(TRANSPORT_DOCUMENTS_ADD_ENDPOINT, headers, transportDocument);
        if (response.isSuccess()) {
            try {
                createdDocumentId = response.getBody().getObject().getInt("id");
                logger.info("A transport document has been added, id = " + createdDocumentId);
            }
            catch (JSONException ex) {
                logger.warn(ex.getMessage());
            }
        }
        return createdDocumentId;
    }

    public List<TransportDocument> getTransportDocuments() {
        List<TransportDocument> documents = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(TRANSPORT_DOCUMENTS_ENDPOINT, headers);
        if (response.isSuccess()) {
            documents = ObjectConverter.convertToObjects(response.getBody().getArray(), TransportDocument.class);
            if (documents == null)
                logger.warn("Failed to map transport documents");
        }
        return documents;
    }
}
