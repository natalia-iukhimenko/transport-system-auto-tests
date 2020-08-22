package ru.iukhimenko.transportsystem.autotesting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;

@JsonDeserialize(builder = TransportDocument.TransportDocumentBuilder.class)
@Builder
public class TransportDocument {
    private final Integer id;
    @JsonProperty("documenttype")
    private final String documentType;
    @JsonProperty
    private final String series;
    @JsonProperty
    private final String number;
    @JsonProperty("issuedby")
    private final String issuedBy;
    @JsonProperty("issueddate")
    private final String issuedDate;
    @JsonProperty("expiredate")
    private final String expireDate;
    @JsonProperty("transportid")
    private final Integer transportId;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class TransportDocumentBuilder {}
}
