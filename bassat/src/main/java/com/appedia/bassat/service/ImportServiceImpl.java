package com.appedia.bassat.service;

import com.appedia.bassat.common.CompressionUtil;
import com.appedia.bassat.common.HashUtil;
import com.appedia.bassat.domain.ImportedStatement;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.persistence.ImportedStatementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ImportedStatementMapper importedStatementMapper;
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
     * @param userEmail
     * @param accountNumber
     * @param fileData
     * @throws IOException
     * @throws DuplicateKeyException
     */
    @Transactional
    public void importStatement(String userEmail, String accountNumber, byte[] fileData) throws ImportException {

        if (userEmail == null || fileData == null) {
            throw new IllegalArgumentException("userEmail and statementPdfFile are required");
        }

        ImportedStatement statement;
        try {
            if (enableCompression) {
                System.out.println("#### COMPRESSING DATA ####");
                fileData = CompressionUtil.compress(fileData);
                System.out.println("File size is now " + fileData.length + " bytes");
            }
            String fileHashKey = HashUtil.hash(new String(fileData), "SHA1");

            statement = new ImportedStatement();
            statement.setPdfFileData(fileData);
            statement.setLinkAccountNumber(accountNumber);
            statement.setLinkUserEmail(userEmail);
            statement.setPdfFileHashKey(fileHashKey);
            statement.setStatus(ImportStatus.PENDING);
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

