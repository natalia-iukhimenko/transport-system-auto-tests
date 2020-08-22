package ru.iukhimenko.transportsystem.autotesting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class Engine {
    @JsonInclude(NON_NULL)
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer volume;
    @JsonProperty
    private String fuel;

    public Engine(String name, Integer volume, String fuel) {
        this.name = name;
        this.volume = volume;
        this.fuel = fuel;
    }
}
