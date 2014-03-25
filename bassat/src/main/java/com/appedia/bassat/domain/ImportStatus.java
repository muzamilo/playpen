package com.appedia.bassat.domain;

/**
 *
 * @author Muz Omar
 */
public enum ImportStatus {

    PENDING("P"),
    ERROR("X"),
    SUCCESS("S");

    private String statusCode;

    private ImportStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

}
