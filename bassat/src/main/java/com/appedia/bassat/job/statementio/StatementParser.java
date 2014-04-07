package com.appedia.bassat.job.statementio;

import com.appedia.bassat.domain.StatementComposite;

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
    StatementComposite parse(List<String> lines) throws ParseException;

}
