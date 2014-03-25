package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.ImportStatement;
import com.appedia.bassat.domain.ImportStatus;

import java.util.List;

/**
 *
 * @author Muz Omar
 */
public interface ImportStatementMapper {

    List<ImportStatement> getImportStatementsByStatus(ImportStatus importStatus);

    void insertImportStatement(ImportStatement importStatement);

}
