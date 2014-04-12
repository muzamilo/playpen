package com.appedia.bassat.service;

import com.appedia.bassat.common.CompressionUtil;
import com.appedia.bassat.common.HashUtil;
import com.appedia.bassat.domain.*;
import com.appedia.bassat.persistence.ImportedStatementMapper;
import com.appedia.bassat.persistence.StatementMapper;
import com.appedia.bassat.persistence.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

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
    public List<ImportedStatement> getImportedStatements(ImportStatus status) throws StatementIntegrityViolationException {
        List<ImportedStatement> statements = importedStatementMapper.getImportedStatementsByStatus(status);
        for (ImportedStatement statement : statements) {
            decompressFileData(statement);
        }
        return statements;
    }

    /**
     *
     * @param importedStatement
     */
    public void updateImportedStatement(ImportedStatement importedStatement) {
        importedStatementMapper.updateImportedStatement(importedStatement);
    }

    /**
     *
     * @param statementComposite
     */
    @Transactional
    public void insertStatement(StatementComposite statementComposite) throws StatementIntegrityViolationException {
        Statement statement = statementComposite.getStatement();
        try {
            statementMapper.insertStatement(statement);

            List<Transaction> transactions = Arrays.asList(statementComposite.getTransactions());
            for (Transaction transaction : transactions) {
                transaction.setStatementId(statement.getStatementId());
                transactionMapper.insertTransaction(transaction);
            }
        } catch (DuplicateKeyException e) {
            throw new StatementIntegrityViolationException("Statement for " + statement.getAccountIdentifier() + " has already been processed", e);
        }
    }

    /**
     *
     * @param importedStatement
     * @param statementComposite
     */
    @Transactional
    public void processImportedStatement(ImportedStatement importedStatement, StatementComposite statementComposite) throws StatementIntegrityViolationException {
        // link this statement to the original PDF source
        statementComposite.getStatement().setImportedStatementId(importedStatement.getImportedStatementId());
        try {
            // persist the statement
            insertStatement(statementComposite);
            // flag the imported statement as processed
            importedStatement.setStatus(ImportStatus.PROCESSED);
        } catch (StatementIntegrityViolationException e) {
            System.err.println(e);
            // flag the imported statement as failed
            importedStatement.setStatus(ImportStatus.ERROR);
        }
        importedStatementMapper.updateImportedStatement(importedStatement);
    }

    /**
     *
     * @param userId
     * @param accountNumber
     * @param fileData
     * @param status
     * @throws StatementIntegrityViolationException
     */
    @Transactional
    public void uploadStatementFile(long userId, String accountNumber, byte[] fileData, ImportStatus status) throws StatementIntegrityViolationException {

        if (fileData == null || fileData.length == 0) {
            throw new IllegalArgumentException("statementPdfFile is required");
        }

        ImportedStatement statement;
        try {
            statement = new ImportedStatement();
            statement.setPdfFileData(CompressionUtil.compress(fileData));
            statement.setLinkAccountNumber(accountNumber);
            statement.setLinkUserId(userId);
            statement.setPdfFileChecksum(HashUtil.hash(Arrays.toString(fileData), "SHA1"));
            statement.setStatus(status);
            statement.setImportDateTime(new Date());
        } catch (IOException e) {
            throw new StatementIntegrityViolationException("Unable to read file data : " + e.toString(), e);
        }

        try {
            importedStatementMapper.insertImportedStatement(statement);
        } catch (DuplicateKeyException e) {
            throw new StatementIntegrityViolationException("Statement for " + accountNumber + " has already been imported", e);
        }
    }

    /**
     * Helper method to decompress the PDF file data in the imported statement
     *
     * @param importedStatement
     */
    protected final void decompressFileData(ImportedStatement importedStatement) throws StatementIntegrityViolationException {
        try {
            importedStatement.setPdfFileData(CompressionUtil.decompress(importedStatement.getPdfFileData()));
        } catch (DataFormatException e) {
            // todo: log.WARN here
            // ignore in case the PDF file data was not compressed and just stored
        } catch (IOException e) {
            throw new StatementIntegrityViolationException("PDF file data could not be decompressed", e);
        }
    }


}
