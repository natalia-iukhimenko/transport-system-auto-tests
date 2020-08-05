package ru.iukhimenko.transportsystem.autotesting.api.objectpools;

import ru.iukhimenko.transportsystem.autotesting.api.service.EngineService;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportModelService;
import ru.iukhimenko.transportsystem.autotesting.api.service.VehicleService;
import ru.iukhimenko.transportsystem.autotesting.core.ObjectPool;
import ru.iukhimenko.transportsystem.autotesting.core.model.Engine;
import ru.iukhimenko.transportsystem.autotesting.core.model.Vehicle;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;
import java.util.concurrent.ConcurrentLinkedQueue;

public class VehiclesPool extends ObjectPool<Vehicle> {
    public VehiclesPool(int minNumberOfElements) {
        super(minNumberOfElements);
    }

    @Override
    protected ConcurrentLinkedQueue<Vehicle> getExistingObjects() {
        VehicleService service = new VehicleService();
        ConcurrentLinkedQueue<Vehicle> vehicles = new ConcurrentLinkedQueue<>(service.getVehicles());
        return vehicles;
    }

    @Override
    protected Vehicle create() {
        EngineService engineService = new EngineService();
        int engineId = engineService.addEngine(TestDataManager.getTestEngine());
        TransportModelService modelService = new TransportModelService();
        int transportModelId = modelService.addTransportModel(TestDataManager.getTestTransportModel());

        Vehicle vehicleToCreate = new Vehicle.VehicleBuilder()
                .setNumber(TestDataManager.getUniqueCarNumber())
                .setTransportModelId(transportModelId)
                .setColor("auto-blue")
                .setEngineId(engineId)
                .setEnginePower(1200)
                .setProducedYear(2015)
                .setStartupDate("2017-12-06")
                .setVin(TestDataManager.getUniqueVinNumber())
                .build();
        new VehicleService().addVehicle(vehicleToCreate);
        return vehicleToCreate;
    }
}
