package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.StatementHeader;

/**
 *
 * @author Muz Omar
 */
public interface StatementMapper {

    /**
     *
     * @param header
     */
    void insertStatementHeader(StatementHeader header);

}
