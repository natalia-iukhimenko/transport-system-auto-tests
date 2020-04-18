package ru.iukhimenko.transportsystem.autotesting.api.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectConverter {
    public static <T> T convertToObject(String json, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        T mappedObject = null;
        try {
            mappedObject = mapper.readValue(json, classType);
        }
        catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mappedObject;
    }
}