package ru.iukhimenko.transportsystem.autotesting.api.objectpools;

import com.github.javafaker.Faker;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportDocumentService;
import ru.iukhimenko.transportsystem.autotesting.core.ObjectPool;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportDocument;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TransportDocumentsPool extends ObjectPool<TransportDocument> {
    public TransportDocumentsPool(int minNumberOfElements) {
        super(minNumberOfElements);
    }

    @Override
    protected ConcurrentLinkedQueue<TransportDocument> getExistingObjects() {
        TransportDocumentService service = new TransportDocumentService();
        ConcurrentLinkedQueue<TransportDocument> documents = new ConcurrentLinkedQueue<TransportDocument>(service.getTransportDocuments());
        return documents;
    }

    @Override
    protected TransportDocument create() {
        Integer vehicleId = new VehiclesPool(1).get().getId();
        TransportDocument documentToCreate = new TransportDocument.TransportDocumentBuilder()
                .setDocumentType("Auto-generated document")
                .setSeries(TestDataManager.getUniqueNumericCombination("###"))
                .setNumber(TestDataManager.getUniqueNumericCombination("#########"))
                .setIssuedDate("2020-02-25")
                .setIssuedBy(new Faker().company().name())
                .setExpireDate("2025-02-25")
                .setTransportId(vehicleId)
                .build();
        TransportDocumentService service = new TransportDocumentService();
        service.addTransportDocument(documentToCreate);
        return documentToCreate;
    }
}
