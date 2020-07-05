package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.model.Vehicle;
import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.*;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.ADMIN_PASSWORD;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.ADMIN_USERNAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleService extends ApiService {
    private Logger logger = LoggerFactory.getLogger(VehicleService.class);
    private User actor;

    public VehicleService() {
        super();
        actor = new User(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    public VehicleService(User actor) {
        super();
        this.actor = actor;
    }

    public Integer addVehicle(Vehicle vehicle) {
        Integer createdVehicleId = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendPostRequest(TRANSPORTS_ADD_ENDPOINT, headers, vehicle);
        if (response.isSuccess()) {
            try {
                createdVehicleId = response.getBody().getObject().getInt("id");
                logger.info("A vehicle has been added, id = " + createdVehicleId);
            }
            catch (JSONException ex) {
                logger.warn(ex.getMessage());
            }
        }
        return createdVehicleId;
    }

    public Vehicle getVehicle(Integer id) {
        Vehicle vehicle = null;
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(TRANSPORTS_TRANSPORT_ENDPOINT(id), headers);
        if (response.isSuccess()) {
            vehicle = ObjectConverter.convertToObject(response.getBody().getObject(), Vehicle.class);
            if (vehicle == null)
                logger.warn("Failed to map a vehicle");
        }
        return vehicle;
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = null;
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(TRANSPORTS_ENDPOINT, headers);
        if (response.isSuccess()) {
            vehicles = ObjectConverter.convertToObjects(response.getBody().getArray(), Vehicle.class);
            if (vehicles == null)
                logger.warn("Failed to map vehicles");
        }
        return vehicles;
    }
}
