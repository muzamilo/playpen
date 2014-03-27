package com.appedia.bassat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: muz
 * Date: 3/13/14
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatementHeader implements Serializable {

    private StatementFrequency frequency;
    private Date fromDate;
    private Date toDate;
    private String sourceReference;
    private long accountNo;

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

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public String toString() {
        return "StatementHeader{" +
                "accountNo=" + accountNo +
                ", sourceReference='" + sourceReference + '\'' +
                ", toDate=" + toDate +
                ", fromDate=" + fromDate +
                ", frequency=" + frequency +
                '}';
    }
}
