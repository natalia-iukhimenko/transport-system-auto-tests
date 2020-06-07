package ru.iukhimenko.transportsystem.autotesting.api.service;

import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;

public class ApiService {
    public ApiService() {
        Unirest.config()
                .addDefaultHeader("Content-Type", "application/json")
                .setObjectMapper(new JacksonObjectMapper());
    }
}
