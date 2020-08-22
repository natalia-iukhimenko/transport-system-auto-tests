package ru.iukhimenko.transportsystem.autotesting.core.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;

@JsonDeserialize(builder = TransportModel.TransportModelBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class TransportModel {
    private final Integer id;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final String producer;
    @JsonProperty("maxweight")
    private final Integer maxWeight;
    @JsonProperty
    private final Integer length;
    @JsonProperty
    private final Integer width;
    @JsonProperty
    private final Integer height;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransportModelBuilder {
    }
}