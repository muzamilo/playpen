package com.appedia.bassat.domain;

import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class Transaction {

    private long statementId;
    private AccountType type;
    private Date txDate;
    private String description;
    private String tag;
    private double amount;

    public Transaction(AccountType type, Date txDate, String description, String tag, double amount) {
        this.type = type;
        this.txDate = txDate;
        this.description = description;
        this.tag = tag;
        this.amount = amount;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
