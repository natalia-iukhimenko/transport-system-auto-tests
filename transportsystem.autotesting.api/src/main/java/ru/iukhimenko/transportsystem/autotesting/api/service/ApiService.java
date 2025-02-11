package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.Unirest;

import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class ApiService {
    public ApiService() {
        Unirest.config()
                .addDefaultHeader("Content-Type", "application/json")
                .defaultBaseUrl(TRANSPORT_SYSTEM_CONFIG.backendBaseUrl())
                .socketTimeout(180000)
                .connectTimeout(180000);
    }
}
