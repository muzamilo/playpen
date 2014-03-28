package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.ImportedStatement;
import com.appedia.bassat.domain.ImportStatus;

import java.util.List;

/**
 *
 * @author Muz Omar
 */
public interface ImportedStatementMapper {

    /**
     *
     * @param importStatus
     * @return
     */
    List<ImportedStatement> getImportedStatementsByStatus(ImportStatus importStatus);

    /**
     *
     * @param importedStatement
     */
    void insertImportedStatement(ImportedStatement importedStatement);

}
