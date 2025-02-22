package ru.iukhimenko.transportsystem.autotesting.api.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ObjectConverter {
    private static final Logger logger = LoggerFactory.getLogger(ObjectConverter.class);

    public static <T> T convertToObject(JSONObject jsonObject, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        T mappedObject = null;
        try {
            mappedObject = mapper.readValue(jsonObject.toString(), classType);
        }
        catch (JsonParseException e) {
            logger.error(e.getMessage());
        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return mappedObject;
    }

    public static <T> List<T> convertToObjects(JSONArray jsonArray, Class<T> classType) {
        List<T> mappedObjects = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            T mappedObject = convertToObject(jsonArray.getJSONObject(i), classType);
            mappedObjects.add(mappedObject);
        }
        return mappedObjects;
    }
}