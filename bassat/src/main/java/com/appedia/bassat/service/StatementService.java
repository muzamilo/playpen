package com.appedia.bassat.service;

import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.domain.ImportedStatement;
import com.appedia.bassat.domain.StatementComposite;

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
    List<ImportedStatement> getImportedStatements(ImportStatus status) throws StatementIntegrityViolationException;

    /**
     *
     * @param importedStatement
     */
    void updateImportedStatement(ImportedStatement importedStatement);

    /**
     *
     * @param statementComposite
     */
    void insertStatement(StatementComposite statementComposite) throws StatementIntegrityViolationException;

    /**
     *
     * @param importedStatement
     * @param statementComposite
     * @throws StatementIntegrityViolationException
     */
    void processImportedStatement(ImportedStatement importedStatement, StatementComposite statementComposite) throws StatementIntegrityViolationException;

    /**
     *
     * @param userId
     * @param accountNumber
     * @param fileData
     * @param status
     * @throws StatementIntegrityViolationException
     */
    void uploadStatementFile(long userId, String accountNumber, byte[] fileData, ImportStatus status) throws StatementIntegrityViolationException;

}
