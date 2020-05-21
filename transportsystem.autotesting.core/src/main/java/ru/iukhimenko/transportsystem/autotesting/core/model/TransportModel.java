package ru.iukhimenko.transportsystem.autotesting.core.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = TransportModel.TransportModelBuilder.class)
public class TransportModel {
    private final Integer id;
    private final String name;
    private final String producer;
    private final Integer maxWeight;
    private final Integer length;
    private final Integer width;
    private final Integer height;

    public TransportModel(TransportModelBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.producer = builder.producer;
        this.maxWeight = builder.maxWeight;
        this.length = builder.length;
        this.width = builder.width;
        this.height = builder.height;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("producer")
    public String getProducer() {
        return producer;
    }

    @JsonProperty("maxweight")
    public Integer getMaxWeight() {
        return maxWeight;
    }

    @JsonProperty("length")
    public Integer getLength() {
        return length;
    }

    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransportModelBuilder {
        private Integer id;
        private String name;
        private String producer;
        private Integer maxWeight;
        private Integer length;
        private Integer width;
        private Integer height;

        @JsonProperty("id")
        public TransportModelBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        @JsonProperty("name")
        public TransportModelBuilder setName(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("producer")
        public TransportModelBuilder setProducer(String producer) {
            this.producer = producer;
            return this;
        }

        @JsonProperty("maxweight")
        public TransportModelBuilder setMaxWeight(Integer maxWeight) {
            this.maxWeight = maxWeight;
            return this;
        }

        @JsonProperty("length")
        public TransportModelBuilder setLength(Integer length) {
            this.length = length;
            return this;
        }

        @JsonProperty("width")
        public TransportModelBuilder setWidth(Integer width) {
            this.width = width;
            return this;
        }

        @JsonProperty("height")
        public TransportModelBuilder setHeight(Integer height) {
            this.height = height;
            return this;
        }

        public TransportModel build() {
            return new TransportModel(this);
        }
    }
}