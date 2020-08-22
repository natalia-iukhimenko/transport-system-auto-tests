package ru.iukhimenko.transportsystem.autotesting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonDeserialize(builder = Vehicle.VehicleBuilder.class)
@Getter
@Builder
public class Vehicle {
    @JsonInclude(NON_NULL)
    private final Integer id;
    @JsonProperty
    private final String number;
    @JsonProperty
    private final String vin;
    @JsonProperty("transportmodelid")
    private final Integer transportModelId;
    @JsonProperty("producedyear")
    private final Integer producedYear;
    @JsonProperty("startupdate")
    private final String startupDate;
    @JsonProperty("writeoffdate")
    private final String writeOffDate;
    @JsonProperty
    private final String color;
    @JsonProperty("enginepower")
    private final Integer enginePower;
    @JsonProperty("engineid")
    private final Integer engineId;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VehicleBuilder {
        @JsonProperty("transportmodel")
        public VehicleBuilder transportModelId(Integer transportModelId) {
            this.transportModelId = transportModelId;
            return this;
        }

        @JsonProperty("engine")
        public VehicleBuilder engineId(Integer engineId) {
            this.engineId = engineId;
            return this;
        }
    }
}

