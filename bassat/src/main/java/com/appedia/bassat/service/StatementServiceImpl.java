package com.appedia.bassat.service;

import com.appedia.bassat.common.CompressionUtil;
import com.appedia.bassat.common.HashUtil;
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
        importedStatementMapper.updateImportedStatementStatus(importedStatementId, status);
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
     * @param userId
     * @param accountNumber
     * @param fileData
     * @param status
     * @throws ImportException
     */
    @Transactional
    public void uploadStatementFile(long userId, String accountNumber, byte[] fileData, ImportStatus status) throws ImportException {

        if (fileData == null || fileData.length == 0) {
            throw new IllegalArgumentException("statementPdfFile is required");
        }

        ImportedStatement statement;
        try {
//            if (enableCompression) {
//                System.out.println("#### COMPRESSING DATA ####");
//                System.out.println("File size was " + fileData.length + " bytes");
//                fileData = CompressionUtil.compress(fileData);
//                System.out.println("File size is now " + fileData.length + " bytes");
//            }
            String fileHashKey = HashUtil.hash(fileData.toString(), "SHA1");

            statement = new ImportedStatement();
            statement.setPdfFileData(fileData);
            statement.setLinkAccountNumber(accountNumber);
            statement.setLinkUserId(userId);
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

}
