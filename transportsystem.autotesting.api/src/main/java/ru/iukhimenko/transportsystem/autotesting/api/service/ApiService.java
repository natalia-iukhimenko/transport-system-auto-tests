package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.Unirest;

import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class ApiService {
    public ApiService() {
        if (!Unirest.config().isRunning()) {
            Unirest.config()
                    .setDefaultHeader("Content-Type", "application/json")
                    .defaultBaseUrl(TRANSPORT_SYSTEM_CONFIG.backendBaseUrl())
                    .socketTimeout(300000)
                    .connectTimeout(180000);
        }
    }
}
