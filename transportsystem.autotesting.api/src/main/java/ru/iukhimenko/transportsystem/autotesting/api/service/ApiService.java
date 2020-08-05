package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;

import static ru.iukhimenko.transportsystem.autotesting.core.Configs.CONFIG_FILE_PATH;

public class ApiService {
    public ApiService() {
        Unirest.config()
                .addDefaultHeader("Content-Type", "application/json")
                .setObjectMapper(new JacksonObjectMapper())
                .defaultBaseUrl(FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "baseURL"));
    }
}
