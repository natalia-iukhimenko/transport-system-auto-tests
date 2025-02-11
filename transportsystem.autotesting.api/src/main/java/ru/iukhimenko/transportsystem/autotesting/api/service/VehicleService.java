package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.iukhimenko.transportsystem.autotesting.api.AppEndpoints.*;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class VehicleService extends ApiService {
    private final Logger logger = LoggerFactory.getLogger(VehicleService.class);
    private final User actor;

    public VehicleService() {
        actor = new User(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword());
    }

    private HttpResponse<JsonNode> postTransportAdd(Vehicle vehicle) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        return Http.sendPostRequest(TRANSPORTS_ADD_ENDPOINT, headers, vehicle);
    }

    public int getAddVehicleResponseStatusCode(Vehicle vehicle) {
        return postTransportAdd(vehicle).getStatus();
    }

    public Integer addVehicle(Vehicle vehicle) {
        Integer createdVehicleId = -1;
        HttpResponse<JsonNode> response = postTransportAdd(vehicle);
        if (response.isSuccess()) {
            try {
                createdVehicleId = response.getBody().getObject().getInt("id");
                logger.info("A vehicle has been added, id = {}", createdVehicleId);
            }
            catch (JSONException ex) {
                logger.warn(ex.getMessage());
            }
        } else {
            logger.warn("POST {} ended up with status = {} - {}", TRANSPORTS_ADD_ENDPOINT, response.getStatus(), response.getStatusText());
        }
        return createdVehicleId;
    }

    public Vehicle getVehicle(Integer id) {
        Vehicle vehicle = Vehicle.builder().build();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(TRANSPORTS_TRANSPORT_ENDPOINT(id), headers);
        if (response.isSuccess()) {
            vehicle = ObjectConverter.convertToObject(response.getBody().getObject(), Vehicle.class);
        } else {
            logger.warn("GET {} ended up with status = {} - {}", TRANSPORTS_TRANSPORT_ENDPOINT(id), response.getStatus(), response.getStatusText());
        }
        return vehicle;
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", new AuthService().getAccessToken(actor));
        HttpResponse<JsonNode> response = Http.sendGetRequest(TRANSPORTS_ENDPOINT, headers);
        if (response.isSuccess()) {
            vehicles = ObjectConverter.convertToObjects(response.getBody().getArray(), Vehicle.class);
        } else {
            logger.warn("GET {} ended up with status = {} - {}", TRANSPORTS_ENDPOINT, response.getStatus(), response.getStatusText());
        }
        return vehicles;
    }
}
