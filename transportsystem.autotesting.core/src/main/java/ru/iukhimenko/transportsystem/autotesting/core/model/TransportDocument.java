package ru.iukhimenko.transportsystem.autotesting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = TransportDocument.TransportDocumentBuilder.class)
public class TransportDocument {
    private final Integer id;
    private final String documentType;
    private final String series;
    private final String number;
    private final String issuedBy;
    private final String issuedDate;
    private final String expireDate;
    private final Integer transportId;

    public TransportDocument(TransportDocument.TransportDocumentBuilder builder) {
        this.id = builder.id;
        this.documentType = builder.documentType;
        this.series = builder.series;
        this.number = builder.number;
        this.issuedBy = builder.issuedBy;
        this.issuedDate = builder.issuedDate;
        this.expireDate = builder.expireDate;
        this.transportId = builder.transportId;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonProperty("documenttype")
    public String getDocumentType() {
        return documentType;
    }

    @JsonProperty("series")
    public String getSeries() {
        return series;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("issuedby")
    public String getIssuedBy() {
        return issuedBy;
    }

    @JsonProperty("issueddate")
    public String getIssuedDate() {
        return issuedDate;
    }

    @JsonProperty("expiredate")
    public String getExpireDate() {
        return expireDate;
    }

    @JsonProperty("transportid")
    public Integer getTransportId() {
        return transportId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransportDocumentBuilder {
        private Integer id;
        private String documentType;
        private String series;
        private String number;
        private String issuedBy;
        private String issuedDate;
        private String expireDate;
        private Integer transportId;

        @JsonProperty("id")
        public TransportDocumentBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        @JsonProperty("documenttype")
        public TransportDocumentBuilder setDocumentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        @JsonProperty("series")
        public TransportDocumentBuilder setSeries(String series) {
            this.series = series;
            return this;
        }

        @JsonProperty("number")
        public TransportDocumentBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        @JsonProperty("issuedby")
        public TransportDocumentBuilder setIssuedBy(String issuedBy) {
            this.issuedBy = issuedBy;
            return this;
        }

        @JsonProperty("issueddate")
        public TransportDocumentBuilder setIssuedDate(String issuedDate) {
            this.issuedDate = issuedDate;
            return this;
        }

        @JsonProperty("expiredate")
        public TransportDocumentBuilder setExpireDate(String expireDate) {
            this.expireDate = expireDate;
            return this;
        }

        @JsonProperty("transportid")
        public TransportDocumentBuilder setTransportId(Integer transportId) {
            this.transportId = transportId;
            return this;
        }

        public TransportDocument build() {
            return new TransportDocument(this);
        }
    }
}
