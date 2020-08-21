package ru.iukhimenko.transportsystem.autotesting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;
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
    private String name;
    private Integer volume;
    private String fuel;

    public Engine(String name, Integer volume, String fuel) {
        this.name = name;
        this.volume = volume;
        this.fuel = fuel;
    }
}
