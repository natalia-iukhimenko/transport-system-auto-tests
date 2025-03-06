package ru.iukhimenko.transportsystem.autotesting.ui.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportDocumentInfo {
    private String documentType;
    private String series;
    private String number;
    private String issuedBy;
    private String issuedDate;
    private String expireDate;
    private String transportNumber;
}
