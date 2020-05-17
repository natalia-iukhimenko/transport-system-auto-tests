package ru.iukhimenko.transportsystem.autotesting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonDeserialize(builder = Vehicle.VehicleBuilder.class)
public class Vehicle {
    private final Integer id;
    private final String number;
    private final String vin;
    private final String transportModelId;
    private final String producedYear;
    private final String startupDate;
    private final String writeOffDate;
    private final String color;
    private final String enginePower;
    private final String engineId;

    private Vehicle(VehicleBuilder vehicleBuilder) {
        this.id = vehicleBuilder.id;
        this.number = vehicleBuilder.number;
        this.vin = vehicleBuilder.vin;
        this.transportModelId = vehicleBuilder.transportModelId;
        this.producedYear = vehicleBuilder.producedYear;
        this.startupDate = vehicleBuilder.startupDate;
        this.writeOffDate = vehicleBuilder.writeOffDate;
        this.color = vehicleBuilder.color;
        this.enginePower = vehicleBuilder.enginePower;
        this.engineId = vehicleBuilder.engineId;
    }
        
    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("vin")
    public String getVin() {
        return vin;
    }

    @JsonProperty("transportmodelid")
    public String getTransportModelId() {
        return transportModelId;
    }

    @JsonProperty("producedyear")
    public String getProducedYear() {
        return producedYear;
    }

    @JsonProperty("startupdate")
    public String getStartupDate() {
        return startupDate;
    }

    @JsonProperty("writeoffdate")
    public String getWriteOffDate() {
        return writeOffDate;
    }

    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    @JsonProperty("enginepower")
    public String getEnginePower() {
        return enginePower;
    }

    @JsonProperty("engineid")
    public String getEngineId() {
        return engineId;
    }

    public static class VehicleBuilder {
        private Integer id;
        private String number;
        private String vin;
        private String transportModelId;
        private String producedYear;
        private String startupDate;
        private String writeOffDate;
        private String color;
        private String enginePower;
        private String engineId;

        @JsonProperty("id")
        public VehicleBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        @JsonProperty("number")
        public VehicleBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        @JsonProperty("vin")
        public VehicleBuilder setVin(String vin) {
            this.vin = vin;
            return this;
        }

        @JsonProperty("transportmodel")
        public VehicleBuilder setTransportModelId(String transportModelId) {
            this.transportModelId = transportModelId;
            return this;
        }

        @JsonProperty("producedyear")
        public VehicleBuilder setProducedYear(String producedYear) {
            this.producedYear = producedYear;
            return this;
        }

        @JsonProperty("startupdate")
        public VehicleBuilder setStartupDate(String startupDate) {
            this.startupDate = startupDate;
            return this;
        }

        @JsonProperty("writeoffdate")
        public VehicleBuilder setWriteOffDate(String writeOffDate) {
            this.writeOffDate = writeOffDate;
            return this;
        }

        @JsonProperty("color")
        public VehicleBuilder setColor(String color) {
            this.color = color;
            return this;
        }

        @JsonProperty("enginepower")
        public VehicleBuilder setEnginePower(String enginePower) {
            this.enginePower = enginePower;
            return this;
        }

        @JsonProperty("engine")
        public VehicleBuilder setEngineId(String engineId) {
            this.engineId = engineId;
            return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }
}

