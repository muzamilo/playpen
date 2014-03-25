package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.ImportStatement;
import com.appedia.bassat.domain.ImportStatus;

import java.util.List;

/**
 *
 * @author Muz Omar
 */
public interface ImportStatementMapper {

    /**
     *
     * @param importStatus
     * @return
     */
    List<ImportStatement> getImportStatementsByStatus(ImportStatus importStatus);

    /**
     *
     * @param importStatement
     */
    void insertImportStatement(ImportStatement importStatement);

}
