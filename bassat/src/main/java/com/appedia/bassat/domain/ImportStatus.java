package com.appedia.bassat.domain;

/**
 *
 * @author Muz Omar
 */
public enum ImportStatus {

    PENDING(0),
    ERROR(-1),
    PROCESSED(1);

    private int statusCode;

    /**
     *
     * @param statusCode
     */
    private ImportStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return
     */
    public int getStatusCode() {
        return statusCode;
    }

}
