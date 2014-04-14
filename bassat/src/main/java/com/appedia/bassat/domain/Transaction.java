package com.appedia.bassat.domain;

import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class Transaction {

    private long statementId;
    private Date txDate;
    private String description;
    private double amount;

    public Transaction(Date txDate, String description, double amount) {
        this.txDate = txDate;
        this.description = description;
        this.amount = amount;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "statementId=" + statementId +
                ", txDate=" + txDate +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}
