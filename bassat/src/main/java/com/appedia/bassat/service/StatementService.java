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
    List<ImportedStatement> getImportedStatements(ImportStatus status);

    /**
     *
     * @param importedStatement
     */
    void updateImportedStatement(ImportedStatement importedStatement);

    /**
     *
     * @param statementComposite
     */
    void insertStatement(StatementComposite statementComposite) throws CreateStatementException;

    /**
     *
     * @param importedStatement
     * @param statementComposite
     * @throws CreateStatementException
     */
    void processImportedStatement(ImportedStatement importedStatement, StatementComposite statementComposite) throws CreateStatementException;

    /**
     *
     * @param userId
     * @param accountNumber
     * @param fileData
     * @param status
     * @throws CreateStatementException
     */
    void uploadStatementFile(long userId, String accountNumber, byte[] fileData, ImportStatus status) throws CreateStatementException;

}
