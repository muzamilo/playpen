package com.appedia.bassat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Muz Omar
 */
public class ImportStatement implements Serializable {

    private long importStatementId;
    private Date importDateTime;
    private String linkUserEmail;
    private String linkAccountNumber;
    private String md5;
    private String pdfBinary;
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

    public String getLinkUserEmail() {
        return linkUserEmail;
    }

    public void setLinkUserEmail(String linkUserEmail) {
        this.linkUserEmail = linkUserEmail;
    }

    public String getLinkAccountNumber() {
        return linkAccountNumber;
    }

    public void setLinkAccountNumber(String linkAccountNumber) {
        this.linkAccountNumber = linkAccountNumber;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPdfBinary() {
        return pdfBinary;
    }

    public void setPdfBinary(String pdfBinary) {
        this.pdfBinary = pdfBinary;
    }

    public ImportStatus getStatus() {
        return status;
    }

    public void setStatus(ImportStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ImportStatement{" +
                "importDateTime=" + importDateTime +
                ", linkUserEmail='" + linkUserEmail + '\'' +
                ", linkAccountNumber='" + linkAccountNumber + '\'' +
                ", md5='" + md5 + '\'' +
                ", pdfBinary='" + pdfBinary + '\'' +
                ", status=" + status +
                '}';
    }
}
