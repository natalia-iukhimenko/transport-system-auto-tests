package ru.iukhimenko.transportsystem.autotesting.api.objectpools;

import ru.iukhimenko.transportsystem.autotesting.api.service.VehicleService;
import ru.iukhimenko.transportsystem.autotesting.core.ObjectPool;
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
        Vehicle vehicle = new Vehicle.VehicleBuilder()
                .setNumber(TestDataManager.getUniqueCarNumber())
                .setTransportModelId(94) // todo
                .setColor("blue")
                .setEngineId(101) // todo
                .setEnginePower(1200)
                .setProducedYear(2015)
                .setStartupDate("2017-12-06")
                .setVin(TestDataManager.getUniqueVinNumber())
                .build();
        return vehicle;
    }
}
