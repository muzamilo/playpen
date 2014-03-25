package com.appedia.bassat.service;

import com.appedia.bassat.domain.ImportStatement;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.persistence.ImportStatementMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ImportStatement> getImportStatementsByStatus(ImportStatus importStatus) {
        return importStatementMapper.getImportStatementsByStatus(importStatus);
    }

    @Transactional
    public void insertImportStatement(ImportStatement importStatement) {
        importStatementMapper.insertImportStatement(importStatement);
    }

}
