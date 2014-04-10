package com.appedia.bassat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class ImportedStatement implements Serializable {

    private long importedStatementId;
    private Date importDateTime;
    private long linkUserId;
    private String linkAccountIdentifier;
    private String pdfFileChecksum;
    private byte[] pdfFileData;
    private ImportStatus status;

    public long getImportedStatementId() {
        return importedStatementId;
    }

    public void setImportedStatementId(long importedStatementId) {
        this.importedStatementId = importedStatementId;
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
        return linkAccountIdentifier;
    }

    public void setLinkAccountNumber(String linkAccountIdentifier) {
        this.linkAccountIdentifier = linkAccountIdentifier;
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
                "importedStatementId=" + importedStatementId +
                ", importDateTime=" + importDateTime +
                ", linkUserId='" + linkUserId + '\'' +
                ", linkAccountIdentifier='" + linkAccountIdentifier + '\'' +
                ", pdfFileChecksum='" + pdfFileChecksum + '\'' +
                ", pdfFileData=(" + pdfFileData.length + " bytes)" +
                ", status=" + status +
                '}';
    }
}
