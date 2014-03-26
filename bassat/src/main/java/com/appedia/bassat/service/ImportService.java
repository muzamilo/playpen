package com.appedia.bassat.service;

import com.appedia.bassat.domain.ImportStatement;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.persistence.ImportStatementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 * @author Muz Omar
 */
@Service
public class ImportService {

    @Autowired
    private ImportStatementMapper importStatementMapper;

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
     * @param importStatement
     */
    @Transactional
    public void persistStatement(ImportStatement importStatement) throws DuplicateKeyException {
        importStatementMapper.insertImportStatement(importStatement);
    }

}
