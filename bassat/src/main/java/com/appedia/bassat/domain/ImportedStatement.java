package com.appedia.bassat.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class ImportedStatement implements Serializable {

    private long importStatementId;
    private Date importDateTime;
    private long linkUserId;
    private String linkAccountNumber;
    private String pdfFileChecksum;
    private byte[] pdfFileData;
    private ImportStatus status;

    public long getImportStatementId() {
        return importStatementId;
    }

    public void setImportStatementId(long importStatementId) {
        this.importStatementId = importStatementId;
    }

    public Date getImportDateTime() {
        return importDateTime;
    }

    public void setImportDateTime(Date importDateTime) {
        this.importDateTime = importDateTime;
    }

    public long getLinkUserId() {
        return linkUserId;
    }

    public void setLinkUserId(long linkUserId) {
        this.linkUserId = linkUserId;
    }

    public String getLinkAccountNumber() {
        return linkAccountNumber;
    }

    public void setLinkAccountNumber(String linkAccountNumber) {
        this.linkAccountNumber = linkAccountNumber;
    }

    public String getPdfFileChecksum() {
        return pdfFileChecksum;
    }

    public void setPdfFileChecksum(String pdfFileChecksum) {
        this.pdfFileChecksum = pdfFileChecksum;
    }

    public byte[] getPdfFileData() {
        return pdfFileData;
    }

    public void setPdfFileData(byte[] pdfFileData) {
        this.pdfFileData = pdfFileData;
    }

    public ImportStatus getStatus() {
        return status;
    }

    public void setStatus(ImportStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ImportedStatement{" +
                "importStatementId=" + importStatementId +
                ", importDateTime=" + importDateTime +
                ", linkUserId='" + linkUserId + '\'' +
                ", linkAccountNumber='" + linkAccountNumber + '\'' +
                ", pdfFileChecksum='" + pdfFileChecksum + '\'' +
                ", pdfFileData=(" + pdfFileData.length + " bytes)" +
                ", status=" + status +
                '}';
    }
}
