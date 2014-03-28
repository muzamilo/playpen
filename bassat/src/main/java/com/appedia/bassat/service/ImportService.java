package com.appedia.bassat.service;

import com.appedia.bassat.domain.ImportedStatement;

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
    List<ImportedStatement> getStatementsToImport();

    /**
     *
     * @return
     */
    List<ImportedStatement> getStatementsToRetryImport();

    /**
     *
     * @param userEmail
     * @param accountNumber
     * @param fileData
     * @throws ImportException
     */
    void importStatement(String userEmail, String accountNumber, byte[] fileData) throws ImportException;

}
