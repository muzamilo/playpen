package com.appedia.bassat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class Statement implements Serializable {

    private long statementId;
    private long importedStatementId;
    private StatementFrequency frequency;
    private Date fromDate;
    private Date toDate;
    private String sourceReference;
    private String accountIdentifier;

    public Statement() {
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

    public long getStatementId() {
        return statementId;
    }

    public void setStatementId(long statementId) {
        this.statementId = statementId;
    }

    public long getImportedStatementId() {
        return importedStatementId;
    }

    public void setImportedStatementId(long importedStatementId) {
        this.importedStatementId = importedStatementId;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "statementId=" + statementId +
                ", importedStatementId=" + importedStatementId +
                ", frequency=" + frequency +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", sourceReference='" + sourceReference + '\'' +
                ", accountIdentifier='" + accountIdentifier + '\'' +
                '}';
    }
}
