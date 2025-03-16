package ru.iukhimenko.transportsystem.autotesting.core;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.PROPERTY_PATH;

@Config.Sources("classpath:" + PROPERTY_PATH)
public interface TransportSystemConfig extends Config {
    String PROPERTY_PATH = "config.properties";
    TransportSystemConfig TRANSPORT_SYSTEM_CONFIG = ConfigFactory.create(TransportSystemConfig.class);

    @DefaultValue("http://localhost")
    String baseUrl();

    @DefaultValue("http://localhost")
    String backendBaseUrl();

    String adminUsername();

    String adminPassword();

    String browser();

    @DefaultValue("local")
    String environment();

    @DefaultValue("false")
    String isHeadless();
}
