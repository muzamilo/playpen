package com.appedia.bassat.service;

import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.domain.ImportedStatement;
import com.appedia.bassat.domain.Statement;

import java.io.File;
import java.util.List;

/**
 *
 * @author Muz Omar
 */
public interface StatementService {

    /**
     *
     * @return
     */
    List<ImportedStatement> getStatementsToImport();

    /**
     *
     * @return
     */
    List<ImportedStatement> getStatementsToRetryImport();

    /**
     *
     * @param statement
     */
    void insertStatement(Statement statement);

    /**
     *
     * @param userEmail
     * @param accountNumber
     * @param pdfFile
     * @param status
     * @throws ImportException
     */
    void uploadStatementFile(String userEmail, String accountNumber, File pdfFile, ImportStatus status) throws ImportException;

}
