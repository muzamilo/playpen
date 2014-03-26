package com.appedia.bassat.service;

import com.appedia.bassat.common.CompressionUtil;
import com.appedia.bassat.common.HashUtil;
import com.appedia.bassat.domain.ImportStatement;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.persistence.ImportStatementMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Muz Omar
 */
@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private ImportStatementMapper importStatementMapper;
    private boolean enableCompression;

    /**
     *
     * @param enableCompression
     */
    public void setEnableCompression(boolean enableCompression) {
        this.enableCompression = enableCompression;
    }

    /**
     *
     * @return
     */
    public List<ImportStatement> getStatementsToImport() {
        return importStatementMapper.getImportStatementsByStatus(ImportStatus.PENDING);
    }

    /**
     *
     * @return
     */
    public List<ImportStatement> getStatementsToRetryImport() {
        return importStatementMapper.getImportStatementsByStatus(ImportStatus.ERROR);
    }

    /**
     *
     * @param userEmail
     * @param accountNumber
     * @param statementPdfFile
     * @throws IOException
     * @throws DuplicateKeyException
     */
    @Transactional
    public void importStatementFile(String userEmail, String accountNumber, File statementPdfFile) throws ImportException {

        if (userEmail == null || statementPdfFile == null) {
            throw new IllegalArgumentException("userEmail and statementPdfFile are required");
        }

        if (!FilenameUtils.getExtension(statementPdfFile.getName()).equalsIgnoreCase("pdf")) {
            throw new UnsupportedOperationException("Unable to import " + statementPdfFile);
        }

        ImportStatement statement;
        try {
            byte[] fileData = IOUtils.toByteArray(new BufferedInputStream(new FileInputStream(statementPdfFile)));
            if (enableCompression) {
                fileData = CompressionUtil.compress(fileData);
                System.out.println("#### COMPRESSING DATA ####");
            }
            String fileHashKey = HashUtil.hash(statementPdfFile, "SHA1");

            statement = new ImportStatement();
            statement.setPdfFileData(fileData);
            statement.setLinkAccountNumber(accountNumber);
            statement.setLinkUserEmail(userEmail);
            statement.setPdfFileHashKey(fileHashKey);
            statement.setStatus(ImportStatus.PENDING);
            statement.setImportDateTime(new Date());
        } catch (IOException e) {
            throw new ImportException("Unable to read file " + statementPdfFile.getName() + ": " + e.toString(), e);
        }

        try {
            importStatementMapper.insertImportStatement(statement);
        } catch (DuplicateKeyException e) {
            throw new ImportException("Statement has already been imported", e);
        }
    }

}

