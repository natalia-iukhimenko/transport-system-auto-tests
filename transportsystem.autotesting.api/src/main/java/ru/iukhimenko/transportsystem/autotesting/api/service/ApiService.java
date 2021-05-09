package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;

import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class ApiService {
    public ApiService() {
        Unirest.config()
                .addDefaultHeader("Content-Type", "application/json")
                .setObjectMapper(new JacksonObjectMapper())
                .defaultBaseUrl(FileUtils.getValueFromProperties(TRANSPORT_SYSTEM_CONFIG.baseUrl(), "baseURL"));
    }
}
