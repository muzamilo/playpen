package com.appedia.bassat.domain;

import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class Transaction {

    private long statementId;
    private Date txDateTime;
    private String rawDescription;
    private String indexedDescription;
    private double amount;

    public Transaction(Date txDateTime, String rawDescription, String indexedDescription, double amount) {
        this.txDateTime = txDateTime;
        this.rawDescription = rawDescription;
        this.indexedDescription = indexedDescription;
        this.amount = amount;
    }

    public Date getTxDateTime() {
        return txDateTime;
    }

    public void setTxDateTime(Date txDateTime) {
        this.txDateTime = txDateTime;
    }

    public String getRawDescription() {
        return rawDescription;
    }

    public void setRawDescription(String rawDescription) {
        this.rawDescription = rawDescription;
    }

    public String getIndexedDescription() {
        return indexedDescription;
    }

    public void setIndexedDescription(String indexedDescription) {
        this.indexedDescription = indexedDescription;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getStatementId() {
        return statementId;
    }

    public void setStatementId(long statementId) {
        this.statementId = statementId;
    }
}
