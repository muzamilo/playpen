package com.appedia.bassat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class StatementHeader implements Serializable {

    private StatementFrequency frequency;
    private Date fromDate;
    private Date toDate;
    private String sourceReference;
    private String accountIdentifier;

    public StatementHeader() {
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

    public String getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(String sourceReference) {
        this.sourceReference = sourceReference;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    @Override
    public String toString() {
        return "StatementHeader{" +
                "accountIdentifier=" + accountIdentifier +
                ", sourceReference='" + sourceReference + '\'' +
                ", toDate=" + toDate +
                ", fromDate=" + fromDate +
                ", frequency=" + frequency +
                '}';
    }
}
