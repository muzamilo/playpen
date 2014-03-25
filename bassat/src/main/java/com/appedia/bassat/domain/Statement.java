package com.appedia.bassat.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: muz
 * Date: 3/21/14
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Statement {

    private String reference;
    private StatementFrequency frequency;
    private Date fromDate;
    private Date toDate;

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
}
