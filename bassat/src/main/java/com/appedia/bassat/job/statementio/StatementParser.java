package com.appedia.bassat.job.statementio;

import com.appedia.bassat.domain.Statement;

import java.util.List;

/**
 * @author muz
 */
public interface StatementParser {

    /**
     *
     * @param lines
     * @throws ParseException
     */
    Statement parse(List<String> lines) throws ParseException;

}
