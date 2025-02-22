package ru.iukhimenko.transportsystem.autotesting.api.objectpools;

import com.github.javafaker.Faker;
import ru.iukhimenko.transportsystem.autotesting.api.service.EngineService;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportModelService;
import ru.iukhimenko.transportsystem.autotesting.api.service.VehicleService;
import ru.iukhimenko.transportsystem.autotesting.core.ObjectPool;
import ru.iukhimenko.transportsystem.autotesting.core.model.Vehicle;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;
import java.util.concurrent.ConcurrentLinkedQueue;

public class VehiclesPool extends ObjectPool<Vehicle> {
    public VehiclesPool() {
        super(1);
    }

    public VehiclesPool(int minNumberOfElements) {
        super(minNumberOfElements);
    }

    @Override
    protected ConcurrentLinkedQueue<Vehicle> getExistingObjects() {
        VehicleService service = new VehicleService();
        return new ConcurrentLinkedQueue<>(service.getVehicles());
    }

    @Override
    protected Vehicle create() {
        Integer engineId = new EngineService().addEngine(TestDataManager.getTestEngine());
        Integer transportModelId = new TransportModelService().addTransportModel(TestDataManager.getTestTransportModel());

        Vehicle vehicleToCreate = Vehicle.builder()
                .number(TestDataManager.getUniqueCarNumber())
                .transportModelId(transportModelId)
                .color(new Faker().color().name())
                .engineId(engineId)
                .enginePower(1200)
                .producedYear(2020)
                .startupDate("2021-12-06")
                .vin(TestDataManager.getUniqueVinNumber())
                .build();
        new VehicleService().addVehicle(vehicleToCreate);
        return vehicleToCreate;
    }
}
