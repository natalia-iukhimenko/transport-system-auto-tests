package ru.iukhimenko.transportsystem.autotesting.api.objectpools;

import com.github.javafaker.Faker;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportDocumentService;
import ru.iukhimenko.transportsystem.autotesting.core.ObjectPool;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportDocument;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TransportDocumentsPool extends ObjectPool<TransportDocument> {
    public TransportDocumentsPool(int minNumberOfElements) {
        super(minNumberOfElements);
    }

    @Override
    protected ConcurrentLinkedQueue<TransportDocument> getExistingObjects() {
        TransportDocumentService service = new TransportDocumentService();
        return new ConcurrentLinkedQueue<>(service.getTransportDocuments());
    }

    @Override
    protected TransportDocument create() {
        Integer vehicleId = new VehiclesPool().get().getId();
        LocalDate issuedDate = LocalDate.now();
        TransportDocument documentToCreate = TransportDocument.builder()
                .documentType("Auto-generated document")
                .series(TestDataManager.getUniqueNumericCombination("###"))
                .number(TestDataManager.getUniqueNumericCombination("#########"))
                .issuedDate(issuedDate.toString())
                .issuedBy(new Faker().company().name())
                .expireDate(issuedDate.plusYears(5).toString())
                .transportId(vehicleId)
                .build();
        new TransportDocumentService().addTransportDocument(documentToCreate);
        return documentToCreate;
    }
}