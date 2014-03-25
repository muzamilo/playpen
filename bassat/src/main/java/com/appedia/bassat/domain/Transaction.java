package com.appedia.bassat.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: muz
 * Date: 3/13/14
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {

    private Date txDateTime;
    private String rawDescription;
    private String indexedDescription;
    private double amount;

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
}
