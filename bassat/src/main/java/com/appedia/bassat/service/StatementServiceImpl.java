package com.appedia.bassat.service;

import com.appedia.bassat.common.CompressionUtil;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.domain.ImportedStatement;
import com.appedia.bassat.domain.StatementComposite;
import com.appedia.bassat.persistence.ImportedStatementMapper;
import com.appedia.bassat.persistence.StatementMapper;
import com.appedia.bassat.persistence.TransactionMapper;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Muz Omar
 */
public class StatementServiceImpl implements StatementService {

    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private ImportedStatementMapper importedStatementMapper;

    private boolean enableCompression;

    /**
     *
     * @return
     */
    public List<ImportedStatement> getStatementsToImport() {
        return importedStatementMapper.getImportedStatementsByStatus(ImportStatus.PENDING);
    }

    /**
     *
     * @return
     */
    public List<ImportedStatement> getStatementsToRetryImport() {
        return importedStatementMapper.getImportedStatementsByStatus(ImportStatus.ERROR);
    }

    /**
     *
     * @param importedStatementId
     * @param status
     */
    public void updateImportedStatementStatus(long importedStatementId, ImportStatus status) {
        importedStatementMapper.setImportedStatementStatus(importedStatementId, status);
    }

    /**
     *
     * @param statementComposite
     */
    @Transactional
    public void insertStatement(StatementComposite statementComposite) {
    }

    /**
     *
     * @param userEmail
     * @param accountNumber
     * @param pdfFile
     * @param status
     * @throws ImportException
     */
    @Transactional
    public void uploadStatementFile(String userEmail, String accountNumber, File pdfFile, ImportStatus status) throws ImportException {

        if (userEmail == null || pdfFile == null) {
            throw new IllegalArgumentException("userEmail and statementPdfFile are required");
        }

        ImportedStatement statement;
        try {
            byte[] fileData = IOUtils.toByteArray(new FileInputStream(pdfFile));
            if (enableCompression) {
                System.out.println("#### COMPRESSING DATA ####");
                fileData = CompressionUtil.compress(fileData);
                System.out.println("File size is now " + fileData.length + " bytes");
            }
            String fileHashKey = Long.toHexString(FileUtils.checksumCRC32(pdfFile));

            statement = new ImportedStatement();
            statement.setPdfFileData(fileData);
            statement.setLinkAccountNumber(accountNumber);
            statement.setLinkUserEmail(userEmail);
            statement.setPdfFileChecksum(fileHashKey);
            statement.setStatus(status);
            statement.setImportDateTime(new Date());
        } catch (IOException e) {
            throw new ImportException("Unable to read file data : " + e.toString(), e);
        }

        try {
            importedStatementMapper.insertImportedStatement(statement);
        } catch (DuplicateKeyException e) {
            throw new ImportException("Statement has already been imported", e);
        }
    }

    /**
     *
     * @param enableCompression
     */
    public void setEnableCompression(boolean enableCompression) {
        this.enableCompression = enableCompression;
    }

}
