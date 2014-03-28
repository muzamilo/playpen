package com.appedia.bassat.domain;

import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class Statement {

    private String reference;
    private StatementFrequency frequency;
    private Date fromDate;
    private Date toDate;
    private String accountNumber;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StatementFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(StatementFrequency frequency) {
        this.frequency = frequency;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
