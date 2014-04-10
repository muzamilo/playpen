package com.appedia.bassat.service;

import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.domain.ImportedStatement;
import com.appedia.bassat.domain.StatementComposite;

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
     * @param importedStatementId
     * @param status
     */
    void updateImportedStatementStatus(long importedStatementId, ImportStatus status);

    /**
     *
     * @param statementComposite
     */
    void insertStatement(StatementComposite statementComposite);

    /**
     *
     * @param userId
     * @param accountNumber
     * @param fileData
     * @param status
     * @throws ImportException
     */
    void uploadStatementFile(long userId, String accountNumber, byte[] fileData, ImportStatus status) throws ImportException;

}
