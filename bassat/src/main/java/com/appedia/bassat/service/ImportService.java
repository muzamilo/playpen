package com.appedia.bassat.service;

import com.appedia.bassat.domain.ImportStatement;

import java.io.File;
import java.util.List;

/**
 *
 * @author muz
 */
public interface ImportService {

    /**
     *
     * @return
     */
    List<ImportStatement> getStatementsToImport();

    /**
     *
     * @return
     */
    List<ImportStatement> getStatementsToRetryImport();

    /**
     *
     * @param userEmail
     * @param accountNumber
     * @param statementPdfFile
     * @throws ImportException
     */
    void importStatementFile(String userEmail, String accountNumber, File statementPdfFile) throws ImportException;

}
