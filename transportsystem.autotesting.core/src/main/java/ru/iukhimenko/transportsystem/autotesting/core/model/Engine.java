package ru.iukhimenko.transportsystem.autotesting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Engine {
    private Integer id;
    private String name;
    private Integer volume;
    private String fuel;

    public Engine() {}

    public Engine(String name, Integer volume, String fuel) {
        this.name = name;
        this.volume = volume;
        this.fuel = fuel;
    }

    @JsonInclude(NON_NULL)
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("volume")
    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    @JsonProperty("volume")
    public Integer getVolume() {
        return volume;
    }

    @JsonProperty("fuel")
    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    @JsonProperty("fuel")
    public String getFuel() {
        return fuel;
    }
}
