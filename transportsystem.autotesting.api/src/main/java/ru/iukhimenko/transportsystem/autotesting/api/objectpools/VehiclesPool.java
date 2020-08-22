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

        Vehicle vehicleToCreate = Vehicle.builder()
                .number(TestDataManager.getUniqueCarNumber())
                .transportModelId(transportModelId)
                .color("auto-blue")
                .engineId(engineId)
                .enginePower(1200)
                .producedYear(2015)
                .startupDate("2017-12-06")
                .vin(TestDataManager.getUniqueVinNumber())
                .build();
        new VehicleService().addVehicle(vehicleToCreate);
        return vehicleToCreate;
    }
}
