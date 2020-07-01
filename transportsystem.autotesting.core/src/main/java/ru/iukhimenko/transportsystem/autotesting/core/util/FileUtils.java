package ru.iukhimenko.transportsystem.autotesting.core.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FileUtils {
    static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String getValueFromProperties(String filePath, String key) {
        Properties props = new Properties();
        String value = null;
        try {
            props.load(FileUtils.class.getResourceAsStream(filePath));
            value = props.getProperty(key);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
        return value;
    }

    public static <T> List<T> readJsonObjectsFromFile(String filePath, Class<T> valueType) {
        List<T> mappedObjects = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + valueType.getName() + ";");
            mappedObjects = Arrays.asList(mapper.readValue(fileInputStream, arrayClass));
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        } catch (JsonParseException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
        return mappedObjects;
    }
}