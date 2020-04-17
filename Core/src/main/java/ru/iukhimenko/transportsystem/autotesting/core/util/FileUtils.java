package ru.iukhimenko.transportsystem.autotesting.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileUtils {
    static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String getValueFromProperties(String filePath, String key) {
        Properties props = new Properties();
        String value = null;
        try {
            props.load(new FileInputStream(filePath));
            value = props.getProperty(key);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
        return value;
    }
}
